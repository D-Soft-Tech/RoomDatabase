package com.example.roomdatabase.view.fragments

import android.app.AlertDialog
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
import com.example.roomdatabase.data.AppConstants.getImageLoadDefinition
import com.example.roomdatabase.data.AppViewModel
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.FragmentUpdateBinding

class UpdateFragment : Fragment() {
    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    private val args by navArgs<UpdateFragmentArgs>()

    private lateinit var viewModel: AppViewModel

    // Views
    private lateinit var userImage: ImageView
    private lateinit var userFirstName: TextView
    private lateinit var userLastName: TextView
    private lateinit var userAge: TextView
    private lateinit var submitUpdate: Button
    private var idOfIncomingUserFromSafeArgs: Int = 0
    private lateinit var inComingUserFromSafeArgs: User
    private var localImageBitmap: Bitmap? = null

    override fun onSaveInstanceState(oldInstanceState: Bundle) {
        super.onSaveInstanceState(oldInstanceState)
        oldInstanceState.clear()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentUpdateBinding.inflate(
            inflater,
            container,
            false
        )

        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        idOfIncomingUserFromSafeArgs = args.idOfClickedUser

        // fetch the user from database using the id
        viewModel.getASingleUser(idOfIncomingUserFromSafeArgs)

        // Get the fetched user by observing the livedata in database
        viewModel.singleUserFromDb.observe(viewLifecycleOwner) {
            inComingUserFromSafeArgs = it

            // Setting values coming in from ListFragment via safeArgs into views
            userFirstName.text = inComingUserFromSafeArgs.firstName
            userLastName.text = inComingUserFromSafeArgs.lastName
            userAge.text = inComingUserFromSafeArgs.age.toString()
            userImage.setImageBitmap(inComingUserFromSafeArgs.profilePicture)
            localImageBitmap = inComingUserFromSafeArgs.profilePicture
        }

        // Initializing views
        userFirstName = binding.updatePageFNameTIET
        userLastName = binding.updatePageLNameTIET
        userAge = binding.updatePageAgeTIET
        userImage = binding.userImage
        submitUpdate = binding.submitUpdateBTN

        // User can update the profile picture at the click of the image
        val profilePictureChooserAction = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            val imageBitMap = MediaStore.Images.Media.getBitmap(
                activity?.contentResolver,
                uri
            )
            getImageLoadDefinition(userImage, imageBitMap) // Draws the image using coil
            localImageBitmap = imageBitMap
        }

        userImage.setOnClickListener {
            profilePictureChooserAction.launch("image/*")
        }

        submitUpdate.setOnClickListener {
            val firstName = userFirstName.text.trim().toString()
            val lastName = userLastName.text.trim().toString()
            val age = userAge.text
            if (localImageBitmap?.let
                { localImageBitmap ->
                    viewModel.validInputFields(
                            firstName,
                            lastName,
                            age as Editable,
                            localImageBitmap
                        )
                } == true
            ) {
                val usersNewDetails = localImageBitmap?.let { localImageBitmap ->
                    User(
                        inComingUserFromSafeArgs.id,
                        Integer.parseInt(age.toString()),
                        firstName, lastName,
                        localImageBitmap
                    )
                }
                if (usersNewDetails != null) {
                    viewModel.updateUser(usersNewDetails)
                }
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listUserFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT)
                    .show()
            }
        }

        // Add menu
        setHasOptionsMenu(true)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
//        ChangeBounds()

        sharedElementEnterTransition = animation
//        sharedElementReturnTransition = animation
//        binding.executePendingBindings()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delet_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            buildDialog(inComingUserFromSafeArgs).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteUser(userToBeRemove: User) {
        viewModel.deleteUser(userToBeRemove)
        Toast.makeText(requireContext(), "User removed from database", Toast.LENGTH_SHORT).show()
        findNavController().navigate(R.id.action_updateFragment_to_listUserFragment)
    }

    private fun buildDialog(userToBeDeleted: User) = AlertDialog.Builder(requireContext()).apply {
        setTitle("Remove ${userToBeDeleted.firstName} from Database")
        setMessage("Are you sure you want to \nremove ${userToBeDeleted.firstName} from the Databse?")
        setPositiveButton("Yes") { _, _ ->
            deleteUser(userToBeDeleted)
        }
        setNegativeButton("No") { _, _ -> }
    }.create()
}

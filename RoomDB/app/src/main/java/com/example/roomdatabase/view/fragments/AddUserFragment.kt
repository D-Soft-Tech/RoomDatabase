package com.example.roomdatabase.view.fragments

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import coil.ImageLoader
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.example.roomdatabase.R
import com.example.roomdatabase.data.AppConstants.REQUEST_CODE_FOR_EXTERNAL_STORAGE
import com.example.roomdatabase.data.AppConstants.checkForPermission
import com.example.roomdatabase.data.AppConstants.getImageLoadDefinition
import com.example.roomdatabase.data.AppConstants.requestForPermission
import com.example.roomdatabase.data.AppViewModel
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.FragmentAddUserBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions

class AddUserFragment : Fragment(), EasyPermissions.PermissionCallbacks {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppViewModel

    // Views
    private lateinit var firstName: TextInputEditText
    private lateinit var lastName: TextInputEditText
    private lateinit var age: TextInputEditText
    private lateinit var addUserBTN: Button
    private lateinit var userImageView: ImageView
    private var imageBitmap: Bitmap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        // Initialization of views
        firstName = binding.firstNameET
        lastName = binding.lastNameET
        age = binding.ageET
        addUserBTN = binding.addNewUserBTN
        userImageView = binding.userProfilePicture

//        val requestPermissionLauncher = registerForActivityResult(
//            ActivityResultContracts.RequestPermission()
//        ) { isGranted: Boolean ->
//            if (isGranted) {
//            } else {
//            }
//        }

        addUserBTN.setOnClickListener {

            val newUserFirstName = firstName.text.toString().trim()
            val newUserLastName = lastName.text.toString().trim()
            val newUserAge = age.text

            lifecycleScope.launch {
                if (newUserAge?.let
                    { userAge ->
                        imageBitmap?.let { userImageBitmap ->
                            viewModel.validInputFields(
                                    newUserFirstName,
                                    newUserLastName,
                                    userAge,
                                    userImageBitmap
                                )
                        }
                    } == true
                ) {
                    val newUser = imageBitmap?.let { imageBitmap ->
                        User(
                            0,
                            newUserAge.toString().toInt(),
                            newUserFirstName,
                            newUserLastName,
                            imageBitmap
                        )
                    }
                    // Save user into DB
                    if (newUser != null) {
                        viewModel.insetUserToDB(newUser)
                    }
                    Toast.makeText(requireContext(), "User Added Successfully", Toast.LENGTH_SHORT)
                        .show()
                    // Navigate to Listings screen
                    findNavController().navigate(R.id.action_addUserFragment_to_listUserFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Please fill out all fields",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            } // End of LifeCycle Scope launch
        }

        // Set the content of the imageView to the image coming from the url 
        // (gotten from the function imageFromUrlToBitmap()) before the user changes 
        // it by clicking on the imageView to select an image of his or her choice

        lifecycleScope.launch {
            userImageView.setImageBitmap(imageFromUrlToBitmap())
            imageBitmap = imageFromUrlToBitmap()
        }

        val profilePictureUri = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            val localImageBitmap = MediaStore.Images.Media.getBitmap(
                activity?.contentResolver,
                uri
            )

            // use Coil to load the image from phone
            getImageLoadDefinition(userImageView, localImageBitmap) // Draws the image using coil

            imageBitmap = localImageBitmap
        }

        // Choose Profile picture
        userImageView.setOnClickListener {
            if (checkForPermission(requireContext())) {
                profilePictureUri.launch("image/*")
            } else {
                requestForPermission(this, REQUEST_CODE_FOR_EXTERNAL_STORAGE, getString(R.string.permission_rationale))
            }
        }

        return view
    }

    private suspend fun imageFromUrlToBitmap() = run {
        val loader = ImageLoader(requireContext())
        val request = ImageRequest.Builder(requireContext())
            .data("https://avatars.githubusercontent.com/u/64334649?v=4")
            .crossfade(true)
            .crossfade(1000)
            .placeholder(R.drawable.image_load_placeholder)
            .transformations(CircleCropTransformation())
            .build()

        // Convert the image to a drawable
        val imageAsDrawable = (loader.execute(request) as SuccessResult).drawable
        (imageAsDrawable as BitmapDrawable).bitmap // Convert the drawable to Bitmap and return it
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {
        when (requestCode) {
            REQUEST_CODE_FOR_EXTERNAL_STORAGE -> {
                Toast.makeText(requireContext(), getString(R.string.permission_granted), Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(requireActivity()).build().show()
        } else {
            Toast.makeText(requireContext(), getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

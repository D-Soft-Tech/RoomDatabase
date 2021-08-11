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
import coil.load
import coil.request.ImageRequest
import coil.request.SuccessResult
import coil.transform.CircleCropTransformation
import com.example.roomdatabase.R
import com.example.roomdatabase.data.AppViewModel
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.FragmentAddUserBinding
import com.google.android.material.textfield.TextInputEditText
import kotlinx.coroutines.launch

class AddUserFragment : Fragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppViewModel

    // Views
    private lateinit var firstName: TextInputEditText
    private lateinit var lastName: TextInputEditText
    private lateinit var age: TextInputEditText
    private lateinit var addUserBTN: Button
    private lateinit var userImageView: ImageView

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

        addUserBTN.setOnClickListener {
            val newUserFirstName = firstName.text.toString().trim()
            val newUserLastName = lastName.text.toString().trim()
            val newUserAge = age.text

            if (newUserAge?.let { it1 -> viewModel.validInputFields(newUserFirstName, newUserLastName, it1) } == true) {
                val newUser = User(0, newUserAge.toString().toInt(), newUserFirstName, newUserLastName)
                // Save user into DB
                viewModel.insetUserToDB(newUser)
                Toast.makeText(requireContext(), "User Added Successfully", Toast.LENGTH_SHORT).show()
                // Navigate to Listings screen
                findNavController().navigate(R.id.action_addUserFragment_to_listUserFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        // Set the content of the imageView to the image coming from the url 
        // (gotten from the function imageFromUrlToBitmap()) before the user changes 
        // it by clicking on the imageView to select an image of his or her choice

        lifecycleScope.launch {
            userImageView.setImageBitmap(imageFromUrlToBitmap())
        }

        val profilePictureUri = registerForActivityResult(
            ActivityResultContracts.GetContent()
        ) { uri ->
            val imageBitmap = MediaStore.Images.Media.getBitmap(
                activity?.contentResolver,
                uri
            )
            
            // use Coil to load the image from phone
            userImageView.load(imageBitmap){
                crossfade(true)
                crossfade(1000)
                placeholder(R.drawable.image_load_placeholder)
                transformations(CircleCropTransformation())
            }
        }

        // Choose Profile picture
        userImageView.setOnClickListener {
            profilePictureUri.launch("image/*")
        }

        return view
    }

    private suspend fun imageFromUrlToBitmap(): Bitmap {
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
        return (imageAsDrawable as BitmapDrawable).bitmap
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

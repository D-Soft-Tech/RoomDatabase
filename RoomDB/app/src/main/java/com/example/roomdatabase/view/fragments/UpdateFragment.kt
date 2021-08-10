package com.example.roomdatabase.view.fragments

import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.roomdatabase.R
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

        val inComingUserFromSafeArgs = args.clickedUser

        // Initializing views
        userFirstName = binding.updatePageFNameTIET
        userLastName = binding.updatePageLNameTIET
        userAge = binding.updatePageAgeTIET
        userImage = binding.imageView
        submitUpdate = binding.submitUpdateBTN

        // Setting values to views
        userFirstName.text = inComingUserFromSafeArgs.firstName
        userLastName.text = inComingUserFromSafeArgs.lastName
        userAge.text = inComingUserFromSafeArgs.age.toString()
        userImage.setImageResource(R.drawable.ic_baseline_person_24)

        submitUpdate.setOnClickListener {
            val firstName = userFirstName.text.trim().toString()
            val lastName = userLastName.text.trim().toString()
            val age = userAge.text
            if (viewModel.validInputFields(firstName, lastName, age as Editable)) {
                val usersNewDetails = User(inComingUserFromSafeArgs.id, Integer.parseInt(age.toString()), firstName, lastName)
                Log.d("UpdateTag", "$usersNewDetails")
                viewModel.updateUser(usersNewDetails)
                Toast.makeText(requireContext(), "Updated Successfully", Toast.LENGTH_SHORT).show()
                findNavController().navigate(R.id.action_updateFragment_to_listUserFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

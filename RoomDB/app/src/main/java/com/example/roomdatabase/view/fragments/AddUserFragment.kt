package com.example.roomdatabase.view.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.roomdatabase.R
import com.example.roomdatabase.data.AppViewModel
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.FragmentAddUserBinding
import com.google.android.material.textfield.TextInputEditText

class AddUserFragment : Fragment() {
    private var _binding: FragmentAddUserBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: AppViewModel

    // Views
    private lateinit var firstName: TextInputEditText
    private lateinit var lastName: TextInputEditText
    private lateinit var age: TextInputEditText
    private lateinit var addUserBTN: Button

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentAddUserBinding.inflate(inflater, container, false)
        val view = binding.root

        viewModel = ViewModelProvider(requireActivity()).get(AppViewModel::class.java)

        // Initialization of views
        firstName = binding.firstNameET
        lastName = binding.lastNameET
        age = binding.ageET
        addUserBTN = binding.addNewUserBTN

        addUserBTN.setOnClickListener {
            val newUserFirstName = firstName.text.toString().trim()
            val newUserLastName = firstName.text.toString().trim()
            val newUserAge = age.text

            if (newUserAge?.let { it1 -> validInputFields(newUserFirstName, newUserLastName, it1) } == true) {
                val newUser = User(Integer.parseInt(age.toString()), newUserFirstName, newUserLastName)
                // Save user into DB
                viewModel.insetUserToDB(newUser)
                Toast.makeText(requireContext(), "User Added Successfully", Toast.LENGTH_SHORT).show()
                // Navigate to Listings screen
                findNavController().navigate(R.id.action_listUserFragment_to_addUserFragment)
            } else {
                Toast.makeText(requireContext(), "Please fill out all fields", Toast.LENGTH_SHORT).show()
            }
        }

        return view
    }

    private fun validInputFields(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.isEmpty())
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

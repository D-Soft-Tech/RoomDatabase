package com.example.roomdatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.databinding.FragmentListUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListUserFragment : Fragment() {

    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!

    // Views
    private lateinit var searchView: SearchView
    private lateinit var fab: FloatingActionButton
    private lateinit var listUserRV: RecyclerView

    // Safe args
    private val args by navArgs<ListUserFragmentArgs>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentListUserBinding.inflate(inflater, container, false)
        val view = binding.root

        // Initializing views
        searchView = binding.searchForUserSv
        fab = binding.addNewUser
        listUserRV = binding.listOfUsersRv

        // Set onClickListener on fab to navigate to add user fragment
        fab.setOnClickListener() {
            findNavController().navigate(R.id.action_listUserFragment_to_addUserFragment)
        }

        return view
    }

    override fun onResume() {
        super.onResume()
        val newUser = args.newUser
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

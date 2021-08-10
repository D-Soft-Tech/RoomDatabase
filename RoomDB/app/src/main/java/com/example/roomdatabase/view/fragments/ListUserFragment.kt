package com.example.roomdatabase.view.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.adapter.ListUserRecyclerViewAdapter
import com.example.roomdatabase.data.AppViewModel
import com.example.roomdatabase.databinding.FragmentListUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ListUserFragment : Fragment() {

    private var _binding: FragmentListUserBinding? = null
    private val binding get() = _binding!!

    // RecyclerView Adapter
    private lateinit var adapter: ListUserRecyclerViewAdapter

    // ViewModel
    private lateinit var viewModel: AppViewModel

    // Views
    private lateinit var searchView: SearchView
    private lateinit var fab: FloatingActionButton
    private lateinit var listUserRV: RecyclerView

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

        // initialize the RecyclerView Adapter
        adapter = ListUserRecyclerViewAdapter()
        // Initialize viewModel
        viewModel = ViewModelProvider(this).get(AppViewModel::class.java)

        listUserRV.adapter = adapter
        listUserRV.layoutManager = LinearLayoutManager(requireContext())

        // Observe the Live data in the view model and use it to set the data to the recyclerview adapter
        viewModel.allUsersInDB.observe(
            viewLifecycleOwner,
            {
                adapter.setData(it)
            }
        )

        // Set onClickListener on fab to navigate to add user fragment
        fab.setOnClickListener {
            findNavController().navigate(R.id.action_listUserFragment_to_addUserFragment)
        }

        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}

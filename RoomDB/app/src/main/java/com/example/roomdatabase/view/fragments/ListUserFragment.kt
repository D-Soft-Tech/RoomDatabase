package com.example.roomdatabase.view.fragments

import android.app.ActivityOptions
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.adapter.ListUserRecyclerViewAdapter
import com.example.roomdatabase.adapter.clickListeners.ListUserRecyclerViewClickListener
import com.example.roomdatabase.data.AppViewModel
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.FragmentListUserBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import androidx.navigation.fragment.FragmentNavigator
import android.util.Pair as UtilPair
import android.util.*
import androidx.navigation.ActivityNavigatorExtras

class ListUserFragment : Fragment(), ListUserRecyclerViewClickListener {

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
        adapter = ListUserRecyclerViewAdapter(this)
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

        // Declare that this fragment has a menu
        setHasOptionsMenu(true)

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

    override fun userClickListener(userId: Int, profilePicImageView: ImageView, firstNameTextView: TextView, lastNameTextView: TextView, ageTextView: TextView, position: Int) {

        val pair1: android.util.Pair<View, String> = UtilPair.create(profilePicImageView, "update_page_image_transition_name")
        val pair2: android.util.Pair<View, String> = UtilPair.create(firstNameTextView, "firstNameTN")
        val pair3: android.util.Pair<View, String> = UtilPair.create(lastNameTextView, "lastNameTN")
        val pair4: android.util.Pair<View, String> = UtilPair.create(ageTextView, "ageTN")

        val optionsCompat = ActivityOptions.makeSceneTransitionAnimation(activity, pair1, pair2, pair3, pair4)
        val extras = ActivityNavigatorExtras(
//            mapOf(
//                profilePicImageView to "update_page_image_transition_name",
//                firstNameTextView to "firstNameTN",
//                lastNameTextView to "lastNameTN",
//                ageTextView to "ageTN"
//            )
//        optionsCompat
        )

        val action = ListUserFragmentDirections.actionListUserFragmentToUpdateFragment(userId)
        findNavController().navigate(action)
    }

    override fun userImageClickListener(user: User) {
        // Call a dialog to show the user image just like in whatsapp
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delet_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.delete) {
            dialogBuilder().show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun dialogBuilder() = AlertDialog.Builder(requireContext()).apply {
        setTitle("Empty the Database")
        setMessage("Are you sure you want to empty the database?")
        setPositiveButton("Yes") { _, _ ->
            deleteAllUsers()
        }
        setNegativeButton("No") { _, _ -> }
    }.create()

//    private fun showImageDialog() = AlertDialog.Builder(requireContext()).apply {
//    }

    private fun deleteAllUsers() {
        viewModel.deleteAllUser()
        Toast.makeText(requireContext(), "Database Emptied", Toast.LENGTH_SHORT).show()
    }
}

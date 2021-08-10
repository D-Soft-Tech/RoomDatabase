package com.example.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.R
import com.example.roomdatabase.adapter.difUtil.ListUserRecyclerViewAdapterDiffUtil
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.RecyclerViewItemViewBinding
import com.example.roomdatabase.recyclerViewViewHolders.ListUserRecyclerViewViewHolder

class ListUserRecyclerViewAdapter : RecyclerView.Adapter<ListUserRecyclerViewViewHolder>() {
    private var listOfUsers: List<User> = arrayListOf()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = ListUserRecyclerViewViewHolder(
        RecyclerViewItemViewBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ListUserRecyclerViewViewHolder, position: Int) {
        holder.apply {
            userFName.text = listOfUsers[position].firstName
            userLName.text = listOfUsers[position].lastName
            userAge.text = listOfUsers[position].age.toString()
            userImage.setImageResource(R.drawable.ic_baseline_person_24)
        }
    }

    override fun getItemCount() = listOfUsers.size

    fun setData(newListOfUsers: List<User>) {
        val diff = ListUserRecyclerViewAdapterDiffUtil(listOfUsers, newListOfUsers)
        val diffResult = DiffUtil.calculateDiff(diff)
        listOfUsers = newListOfUsers
        diffResult.dispatchUpdatesTo(this)
    }
}

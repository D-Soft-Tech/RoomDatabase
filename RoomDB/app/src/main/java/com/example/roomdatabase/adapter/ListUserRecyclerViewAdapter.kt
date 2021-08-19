package com.example.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.adapter.clickListeners.ListUserRecyclerViewClickListener
import com.example.roomdatabase.adapter.difUtil.ListUserRecyclerViewAdapterDiffUtil
import com.example.roomdatabase.data.User
import com.example.roomdatabase.databinding.RecyclerViewItemViewBinding
import com.example.roomdatabase.recyclerViewViewHolders.ListUserRecyclerViewViewHolder

class ListUserRecyclerViewAdapter(
    private val userClickListener: ListUserRecyclerViewClickListener
) : RecyclerView.Adapter<ListUserRecyclerViewViewHolder>() {
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
            userImage.setImageBitmap(listOfUsers[position].profilePicture)

            // Set onClickListener on the constraintLayout
            textViewsContainer.setOnClickListener {
                userClickListener.userClickListener(listOfUsers[position].id, userImage, userFName, userLName, userAge, position)
            }

            // Click listener
            userImage.setOnClickListener() {
                userClickListener.userImageClickListener(listOfUsers[position])
            }
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

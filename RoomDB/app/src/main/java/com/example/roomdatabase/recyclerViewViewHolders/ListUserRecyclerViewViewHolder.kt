package com.example.roomdatabase.recyclerViewViewHolders

import androidx.recyclerview.widget.RecyclerView
import com.example.roomdatabase.databinding.RecyclerViewItemViewBinding

class ListUserRecyclerViewViewHolder(binding: RecyclerViewItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
    val userImage = binding.userImage
    val userFName = binding.firstName
    val userLName = binding.lastName
    val userAge = binding.age
}
package com.example.roomdatabase.adapter.clickListeners

import android.widget.ImageView
import android.widget.TextView
import com.example.roomdatabase.data.User

interface ListUserRecyclerViewClickListener {
    fun userClickListener(userId: Int, profilePicImageView: ImageView, firstNameTextView: TextView, lastNameTextView: TextView, ageTextView: TextView, position: Int)
    fun userImageClickListener(user: User)
}

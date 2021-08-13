package com.example.roomdatabase.data

import android.graphics.Bitmap
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,

    var age: Int,
    var firstName: String,
    var lastName: String,
    var profilePicture: Bitmap
)

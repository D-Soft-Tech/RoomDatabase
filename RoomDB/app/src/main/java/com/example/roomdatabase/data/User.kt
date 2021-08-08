package com.example.roomdatabase.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class User(
    var age: Int,
    var firstName: String,
    var lastName: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

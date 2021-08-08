package com.example.roomdatabase.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Entity(tableName = "user_table")
@Parcelize
data class User(
    var age: Int,
    var firstName: String,
    var lastName: String
) : Parcelable {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}

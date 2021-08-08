package com.example.roomdatabase.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface DbDao {

    @Query("SELECT * FROM user_table ORDER BY id ASC")
    fun getUsers(): List<User>

    @Insert
    suspend fun insetUser(user: User)
}

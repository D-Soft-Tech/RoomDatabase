package com.example.roomdatabase.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface DbDao {

    @Query("SELECT * FROM user_table ORDER BY firstName ASC")
    fun getAllUsers(): LiveData<List<User>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insetUser(user: User)

    @Update
    suspend fun updateUser(userToBeUpdated: User)

    @Delete
    suspend fun deleteUser(userToBeDeleted: User)

    @Query("DELETE FROM user_table")
    suspend fun deleteAllUsers()

    @Query("SELECT * FROM user_table WHERE id = :userId")
    fun getASingleUser(userId: Int): LiveData<User>
}

package com.example.roomdatabase.data

import androidx.lifecycle.LiveData

class Repository(private val localDbDao: DbDao) {

    val allUsersInDB: LiveData<List<User>> = localDbDao.getAllUsers()

    suspend fun insertUser(user: User) {
        localDbDao.insetUser(user)
    }

    suspend fun updateUser(userToBeUpdated: User) {
        localDbDao.updateUser(userToBeUpdated)
    }

    suspend fun deleteUser(userToBeDeleted: User) {
        localDbDao.deleteUser(userToBeDeleted)
    }

    suspend fun deleteAllUsers() {
        localDbDao.deleteAllUsers()
    }

    fun getASingleUser(userId: Int): LiveData<User> {
        return localDbDao.getASingleUser(userId)
    }
}

package com.example.roomdatabase.data

import android.app.Application
import android.text.Editable
import android.text.TextUtils
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val allUsersInDB: LiveData<List<User>>

    private var repository: Repository

    init {
        val dbDao = AppDB.getDatabase(application).userDao() // created an instance of DbDao via the database (i.e AppDB)
        repository = Repository(dbDao) // passed the dbDao created above to initialize the repository
        allUsersInDB = repository.allUsersInDB // initialize the above created variable allUserInDB
    }

    fun insetUserToDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) { repository.insertUser(user) }
    }

    fun updateUser(userToBeUpdated: User) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateUser(userToBeUpdated) }
    }

    fun validInputFields(firstName: String, lastName: String, age: Editable): Boolean {
        return !(TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.trim().isEmpty())
    }
}

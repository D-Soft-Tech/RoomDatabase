package com.example.roomdatabase.data

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    private var _allUsersInDB: LiveData<List<User>>
    private var _readUsersInDB: MutableLiveData<List<User>>? = null
    var readUsersInDB: LiveData<List<User>> = _readUsersInDB ?: getUsers()

    private var repository: Repository

    init {
        val dbDao = AppDB.invoke(application).userDao() // created an instance of DbDao via the database (i.e AppDB)
        repository = Repository(dbDao) // passed the dbDao created above to initialize the repository
        _allUsersInDB = repository.allUsersInDB // initialize the above created variable allUserInDB
    }

    fun getUsers() = repository.allUsersInDB

    fun insetUserToDB(user: User) = viewModelScope.launch(Dispatchers.IO) { repository.insertUser(user) }
}

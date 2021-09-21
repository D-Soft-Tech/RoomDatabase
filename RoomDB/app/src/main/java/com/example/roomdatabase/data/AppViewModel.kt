package com.example.roomdatabase.data

import android.app.Application
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.text.Editable
import android.text.TextUtils
import android.widget.ImageView
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AppViewModel(application: Application) : AndroidViewModel(application) {

    val allUsersInDB: LiveData<List<User>>
    private var _singleUserFromDb: LiveData<User> = MutableLiveData()
    val singleUserFromDb: LiveData<User> get() = _singleUserFromDb

    private var repository: Repository

    init {
        val dbDao = AppDB.getDatabase(application)
            .getUserDao() // created an instance of DbDao via the database (i.e AppDB)
        repository =
            Repository(dbDao) // passed the dbDao created above to initialize the repository
        allUsersInDB = repository.allUsersInDB // initialize the above created variable allUserInDB
    }

    fun insetUserToDB(user: User) {
        viewModelScope.launch(Dispatchers.IO) { repository.insertUser(user) }
    }

    fun updateUser(userToBeUpdated: User) {
        viewModelScope.launch(Dispatchers.IO) { repository.updateUser(userToBeUpdated) }
    }

    fun validInputFields(
        firstName: String,
        lastName: String,
        age: Editable,
        profilePicBitmap: Bitmap
    ): Boolean {
        return !(
            TextUtils.isEmpty(firstName) && TextUtils.isEmpty(lastName) && age.trim()
                .isEmpty() && profilePicBitmap.byteCount == 0
            )
    }

    fun deleteUser(userToBeDeleted: User) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteUser(userToBeDeleted)
        }
    }

    fun deleteAllUser() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllUsers()
        }
    }

    fun getBitmapFromImageView(imageView: ImageView): Bitmap =
        ((imageView.drawable) as BitmapDrawable).bitmap

    fun getASingleUser(userId: Int) {
        viewModelScope.launch {
            _singleUserFromDb = repository.getASingleUser(userId)
        }
    }
}

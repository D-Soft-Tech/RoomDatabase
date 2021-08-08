package com.example.roomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [User::class], version = 1, exportSchema = false)
abstract class AppDB : RoomDatabase() {
    abstract fun UserDao(): DbDao

    companion object {
        @Volatile
        private var DB_INSTANCE: AppDB? = null

        operator fun invoke(context: Context) = synchronized(this) {
            val tempInstance = DB_INSTANCE
            if (tempInstance == null) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "appDB"
                ).build()
                DB_INSTANCE = instance
            }
            tempInstance
        }
    }
}

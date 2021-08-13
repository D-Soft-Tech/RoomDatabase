package com.example.roomdatabase.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [User::class], version = 1, exportSchema = false)
@TypeConverters(RoomTypeConverters::class)
abstract class AppDB : RoomDatabase() {
    abstract fun userDao(): DbDao

    companion object {
        @Volatile
        private var DB_INSTANCE: AppDB? = null

        fun getDatabase(context: Context): AppDB {
            val tempInstance = DB_INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            // else the synchronized block below will be executed
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDB::class.java,
                    "appDB"
                ).build()
                DB_INSTANCE = instance
                return instance
            }
        }
    }
}

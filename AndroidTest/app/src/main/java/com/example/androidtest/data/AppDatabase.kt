package com.example.androidtest.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.androidtest.data.daos.UserDao
import com.example.androidtest.models.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getUserDao() : UserDao

    companion object {
        private var instance: AppDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instance ?: synchronized(LOCK) {
            instance ?: buildDatabase(context).also { instance = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context, AppDatabase::class.java, "AndroidTest.db").build()
    }
}
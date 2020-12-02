package com.example.androidtest.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class UserModel(@PrimaryKey val userId : Int, val userName : String)
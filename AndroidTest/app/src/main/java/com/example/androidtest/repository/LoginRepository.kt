package com.example.androidtest.repository

import android.content.Context
import com.example.androidtest.data.ApiClient
import com.example.androidtest.data.AppDatabase
import com.example.androidtest.data.UserApi
import com.example.androidtest.models.LoginRequestModel
import com.example.androidtest.models.UserModel

class LoginRepository(context: Context? = null) {

    private val api: UserApi by lazy {
        ApiClient.createService(UserApi::class.java)
    }

    private var db: AppDatabase? = null

    init {
        if(context != null) {
            db = AppDatabase(context)
        }
    }

    suspend fun login(loginRequestModel: LoginRequestModel) = api.loginAPI(loginRequestModel)

    suspend fun insertUserData(userModel: UserModel) = db?.getUserDao()?.addUser(userModel)
}
package com.example.androidtest.viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.liveData
import com.example.androidtest.MyApp
import com.example.androidtest.R
import com.example.androidtest.data.AppDatabase
import com.example.androidtest.helper.State
import com.example.androidtest.models.LoginRequestModel
import com.example.androidtest.models.UserModel
import com.example.androidtest.repository.LoginRepository
import kotlinx.coroutines.Dispatchers

class LoginViewModel(app: Application) : AndroidViewModel(app) {

    fun login(loginRequestModel: LoginRequestModel) = liveData(Dispatchers.IO) {
        emit(State.loading())

        val response = LoginRepository(getApplication()).login(loginRequestModel)

        if (response.isSuccessful) {
            Log.e("tagss", "LoginResponse = ${response.body()!!.toString()}")
            if(response.body()!!.errorCode == "00") {
                emit(State.success(response.body()!!.user))
            } else if(response.body()!!.errorCode == "01") {
                emit(State.error(response.body()!!.errorMessage, null))
            }
        } else {
            emit(State.error(getApplication<MyApp>().getString(R.string.api_fail), null))
        }

    }

    fun insertUser(userModel: UserModel) = liveData(Dispatchers.IO) {
        emit(State.loading())

        val data = LoginRepository(getApplication()).insertUserData(userModel)
        if(data != null) {
            emit(State.success(null))
        } else {
            emit(State.error("Fail to insert data in local db", null))
        }
    }
}
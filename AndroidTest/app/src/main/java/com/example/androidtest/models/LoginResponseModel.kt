package com.example.androidtest.models

data class LoginResponseModel(val errorCode: String, val errorMessage: String, val user: UserModel? = null)
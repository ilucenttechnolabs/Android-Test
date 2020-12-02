package com.example.androidtest.data

import com.example.androidtest.models.LoginRequestModel
import com.example.androidtest.models.LoginResponseModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface UserApi {
    @POST("login")
    suspend fun loginAPI(@Body loginRequestModel: LoginRequestModel) : Response<LoginResponseModel>
}
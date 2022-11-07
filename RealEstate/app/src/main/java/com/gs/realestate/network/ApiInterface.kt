package com.gs.realestate.network

import retrofit2.Response
import retrofit2.http.*

interface ApiInterface {
    @GET("/api/users?page=2")
    suspend fun getAllUsers(): Response<String>

    @POST("/user/get-otp")
    suspend fun getOtp(@Body otpRequest: OtpRequest):Response<GetOtp>

    @POST("/user/verify-otp")
    suspend fun verifyOtp(@Body otpVerify: OtpVerify):Response<VerifyOtp>


    @Headers("Content-Type: application/json")
    @POST("/user/auth")
    suspend fun authentication(@Body request: AuthenticationRequest):Response<AuthenticationResponse>

    @POST("/verify-user")
    suspend fun verifyUser():Response<AuthenticationResponse>

    @GET("/api/property/myproperties")
    suspend fun getMyProperties(@Header("Cookie") cookie: String):Response<AllPropertiesResponse>
}
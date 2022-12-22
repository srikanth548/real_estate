package com.gs.realestate.network

import com.gs.realestate.network.models.property.CommercialPropertyRequest
import com.gs.realestate.network.models.property.PostAgricultureRequest
import com.gs.realestate.network.models.property.PostResidentialPropertyRequest
import com.gs.realestate.network.models.propertyType.PropertyTypesResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
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

    @GET("place/nearbysearch/json?")
    fun doPlaces(
        @Query(value = "type", encoded = true) type: String?,
        @Query(value = "location", encoded = true) location: String?,
        @Query(value = "name", encoded = true) name: String?,
        @Query(value = "opennow", encoded = true) opennow: Boolean,
        @Query(value = "rankby", encoded = true) rankby: String?,
        @Query(value = "key", encoded = true) key: String?
    ): Call<PlacesPOJO.Root?>


    @Multipart
    @POST("/api/property/uploadFile")
    suspend fun syncImageToServer(
        @Header("X-CSRFToken") crsfToken: String,
        @Part file: MultipartBody.Part,
        @Part("uploaded_slno") serialNo: Int,
        @Query("id") uuid: String
    ): Response<ImageUploadResponse>



    @POST("api/property/list")
    suspend fun syncAgricultureProperty(
        @Header("Authorization") token: String,
        @Header("X-CSRFToken") crsfToken: String,
        @Query("id") uuid: String,
        @Body postAgricultureRequest: PostAgricultureRequest
    ): Response<PropertyUploadResponse>


    @POST("api/property/list")
    suspend fun syncResidentialProperty(
        @Header("Authorization") token: String,
        @Header("X-CSRFToken") crsfToken: String,
        @Query("id") uuid: String,
        @Body postResidentialPropertyRequest: PostResidentialPropertyRequest
    ): Response<PropertyUploadResponse>


    @POST("api/property/list")
    suspend fun syncCommercialProperty(
        @Header("Authorization") token: String,
        @Header("X-CSRFToken") crsfToken: String,
        @Query("id") uuid: String,
        @Body postCommercialPropertyRequest: CommercialPropertyRequest
    ): Response<PropertyUploadResponse>


    @GET("api/property/property-types")
    suspend fun fetchPropertyTypes(): Response<PropertyTypesResponseModel>
}
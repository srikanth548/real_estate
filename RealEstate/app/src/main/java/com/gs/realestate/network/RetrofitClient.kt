package com.gs.realestate.network

import android.content.Context
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.csrftoken
import com.gs.realestate.util.PreferenceHelper.messages
import com.gs.realestate.util.PreferenceHelper.sessionid
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


object RetrofitClient {

    fun getInstance(context: Context): Retrofit {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val prefs = PreferenceHelper.customPreference(context)
        val cookie = "messages="+prefs.messages+";csrftoken="+prefs.csrftoken+";sessionid="+prefs.sessionid

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(ReceivedCookiesInterceptor(context))
            .addInterceptor(Interceptor { chain ->
                val request: Request = chain.request().newBuilder().addHeader("Cookie", cookie).build()
                chain.proceed(request)
            })
            .build()


        return Retrofit.Builder()
            .baseUrl("http://stage.rightmyproperty.in")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
    }

    const val GOOGLE_PLACE_API_KEY = "YOUR_API_KEY"

    var base_url = "https://maps.googleapis.com/maps/api/"

    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =
            OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS).writeTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build()


        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }



}
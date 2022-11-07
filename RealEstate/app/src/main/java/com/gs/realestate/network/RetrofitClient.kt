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
            .baseUrl("http://20.197.3.174:8081")
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .build()
    }

}
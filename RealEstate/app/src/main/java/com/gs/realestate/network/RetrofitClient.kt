package com.gs.realestate.network

import android.content.Context
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.baseUrl
import com.gs.realestate.util.PreferenceHelper.csrftoken
import com.gs.realestate.util.PreferenceHelper.messages
import com.gs.realestate.util.PreferenceHelper.sessionid
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.security.SecureRandom
import java.security.cert.CertificateException
import java.security.cert.X509Certificate
import java.util.concurrent.TimeUnit
import javax.net.ssl.*


object RetrofitClient {

    fun getInstance(context: Context): Retrofit {
        val mHttpLoggingInterceptor = HttpLoggingInterceptor()
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        val prefs = PreferenceHelper.customPreference(context)
        val cookie =
            "messages=" + prefs.messages + ";csrftoken=" + prefs.csrftoken + ";sessionid=" + prefs.sessionid

        val mOkHttpClient = OkHttpClient
            .Builder()
            .addInterceptor(mHttpLoggingInterceptor)
            .addInterceptor(ReceivedCookiesInterceptor(context))
            .addInterceptor(Interceptor { chain ->
                val request: Request =
                    chain.request().newBuilder().addHeader("Cookie", cookie).build()
                chain.proceed(request)
            })
            .build()


        var baseUrl = ""
        baseUrl = if (prefs.baseUrl.isNullOrEmpty()) {
            "https://stage.rightmyproperty.in"
        } else {
            prefs.baseUrl ?: ""
        }

        prefs.baseUrl = baseUrl

        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(mOkHttpClient)
            .client(getUnsafeOkHttpClient())
            .build()
    }


    fun getUnsafeOkHttpClient(): OkHttpClient {
        return try {
            // Create a trust manager that does not validate certificate chains
            val trustAllCerts: Array<TrustManager> = arrayOf<TrustManager>(
                object : X509TrustManager {
                    @Throws(CertificateException::class)
                    override fun checkClientTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    @Throws(CertificateException::class)
                    override fun checkServerTrusted(
                        chain: Array<X509Certificate?>?,
                        authType: String?
                    ) {
                    }

                    override fun getAcceptedIssuers(): Array<X509Certificate> {
                        return arrayOf()
                    }
                }
            )

            // Install the all-trusting trust manager
            val sslContext: SSLContext = SSLContext.getInstance("SSL")
            sslContext.init(null, trustAllCerts, SecureRandom())

            // Create an ssl socket factory with our all-trusting manager
            val sslSocketFactory: SSLSocketFactory = sslContext.getSocketFactory()
            val builder = OkHttpClient.Builder()

            builder.sslSocketFactory(sslSocketFactory, trustAllCerts[0] as X509TrustManager)
            builder.hostnameVerifier(object : HostnameVerifier {
                override fun verify(hostname: String?, session: SSLSession?): Boolean {
                    return true
                }
            })
            builder.build()
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    const val GOOGLE_PLACE_API_KEY = "YOUR_API_KEY"

    var base_url = "https://maps.googleapis.com/maps/api/"

    fun getClient(): Retrofit? {
        val interceptor = HttpLoggingInterceptor()
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)
        val client: OkHttpClient =
            OkHttpClient.Builder().readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS).addInterceptor(interceptor)
                .build()


        return Retrofit.Builder()
            .baseUrl(base_url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }


}
package com.gs.realestate.network

import android.content.Context
import android.util.Log
import com.gs.realestate.util.PreferenceHelper
import com.gs.realestate.util.PreferenceHelper.csrftoken
import com.gs.realestate.util.PreferenceHelper.messages
import com.gs.realestate.util.PreferenceHelper.sessionid
import okhttp3.Interceptor
import okhttp3.Response
import okio.IOException


class ReceivedCookiesInterceptor (context: Context) : Interceptor {

    var cont: Context

    init {
        cont = context
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse: Response = chain.proceed(chain.request())
        val prefs = PreferenceHelper.customPreference(cont)

        Log.i("cookies {}", originalResponse.toString())
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookies: HashSet<String> = HashSet()
            for (header in originalResponse.headers("Set-Cookie")) {
                val strs = header.split("=").toList()
                if(strs.size>1) {
                    val valueList = strs.get(1).split(";").toList()
                    if(valueList.size>1){
                        if(strs.get(0).equals("messages")){
                            prefs.messages = valueList.get(0)
                        }
                        else if(strs.get(0).equals("csrftoken")){
                            prefs.csrftoken = valueList.get(0)
                        }else if(strs.get(0).equals("sessionid")){
                            prefs.sessionid = valueList.get(0)
                        }
                    }
                }
                Log.i("header", header)
                cookies.add(header)
            }

//            Preferences.getDefaultPreferences().edit()
//                .putStringSet(Preferences.PREF_COOKIES, cookies)
//                .apply()
            Log.i("cookies {}", cookies.toString())
            prefs.sessionid?.let { Log.i("session {}", it) }

        }
        return originalResponse
    }
}
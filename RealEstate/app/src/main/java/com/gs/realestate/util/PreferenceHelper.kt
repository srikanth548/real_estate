package com.gs.realestate.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {

    val BASE_URL = "BASE_URL"

    val MOBILE_NUMBER = "MOBILE_NUMBER"
    val USER_PASSWORD = "PASSWORD"
    val CSRFTOKEN = "csrftoken"
    val SESSIONID = "sessionid"
    val MESSAGES = "messages"
    val AUTH_TOKEN = "authToken"

    val CUSTOM_PREF_NAME = "real_estate"

    fun defaultPreference(context: Context): SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context): SharedPreferences =
        context.getSharedPreferences(CUSTOM_PREF_NAME, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.mobilenumber
        get() = getString(MOBILE_NUMBER, "")
        set(value) {
            editMe {
                it.putString(MOBILE_NUMBER, value)
            }
        }

    var SharedPreferences.messages
        get() = getString(MESSAGES, "")
        set(value) {
            editMe {
                it.putString(MESSAGES, value)
            }
        }
    var SharedPreferences.csrftoken
        get() = getString(CSRFTOKEN, "")
        set(value) {
            editMe {
                it.putString(CSRFTOKEN, value)
            }
        }
    var SharedPreferences.sessionid
        get() = getString(SESSIONID, "")
        set(value) {
            editMe {
                it.putString(SESSIONID, value)
            }
        }

    var SharedPreferences.password
        get() = getString(USER_PASSWORD, "")
        set(value) {
            editMe {
                it.putString(USER_PASSWORD, value)
            }
        }

    var SharedPreferences.clearValues
        get() = { }
        set(value) {
            editMe {
                it.clear()
            }
        }


    var SharedPreferences.baseUrl
        get() = getString(BASE_URL, "")
        set(value) {
            editMe {
                it.putString(BASE_URL, value)
            }
        }

    var SharedPreferences.authToken
        get() = getString(AUTH_TOKEN, "")
        set(value) {
            editMe {
                it.putString(AUTH_TOKEN, value)
            }
        }
}

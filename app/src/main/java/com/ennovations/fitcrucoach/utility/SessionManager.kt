package com.ennovations.fitcrucoach.utility

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.databinding.library.baseAdapters.BuildConfig
import com.ennovations.fitcrucoach.Network.Constants
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.quickbox.db.DbHelper
import com.ennovations.fitcrucoach.response.LoginResponse
import com.google.gson.Gson
import com.orhanobut.hawk.Hawk
import com.quickblox.auth.session.QBSettings
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber


//User default credentials
const val DEFAULT_USER_PASSWORD = "quickblox"

/**
 * Credentials I have used

static let applicationID:UInt = 99297
static let authKey = "kH7EdmLsByMjqYX"
static let authSecret = "czG5A7QWftLmADV"
static let accountKey = "Q6vE-2Rt3hpCXPpHrL4f"
Shivam please use these credentials for quickblox so that ios and android both will be on same sync
 */

//App credentials
/*private const val APPLICATION_ID = "98837"
private const val AUTH_KEY = "C8ZZV6OHBpYMePg"
private const val AUTH_SECRET = "vxvkUzHS3HFfNV3"
private const val ACCOUNT_KEY = "xdJ3K9AUXznLpoT4sCPh"*/

private const val APPLICATION_ID = "99297"
private const val AUTH_KEY = "kH7EdmLsByMjqYX"
private const val AUTH_SECRET = "czG5A7QWftLmADV"
private const val ACCOUNT_KEY = "Q6vE-2Rt3hpCXPpHrL4f"


//Chat settings
const val USER_DEFAULT_PASSWORD = "quickblox"
const val CHAT_PORT = 5223
const val SOCKET_TIMEOUT = 300
const val KEEP_ALIVE: Boolean = true
const val USE_TLS: Boolean = true
const val AUTO_JOIN: Boolean = false
const val AUTO_MARK_DELIVERED: Boolean = true
const val RECONNECTION_ALLOWED: Boolean = true
const val ALLOW_LISTEN_NETWORK: Boolean = true


@HiltAndroidApp
class SessionManager : Application() {

    private lateinit var dbHelper: DbHelper

    /**
     * INITIALIZATION FOR SESSION
     */


    companion object {

        private var context: Context? = null
        private var editor: SharedPreferences.Editor? = null
        private var auth: SessionManager? = null
        private var preferences: SharedPreferences? = null

        /**
         * SETTER FOR SESSION DATA
         */
        fun setIsLoggedIn(value: Boolean): Boolean {
            return saveBoolean(Constants.IS_LOGGED_IN, value)
        }

        fun setCoachDetails(value: Any): Any {
            return saveObject(Constants.COACH_DETAIL, value)
        }

        fun getCoachDetails(): LoginResponse.Data {
            return Gson().fromJson(
                getString(Constants.COACH_DETAIL),
                LoginResponse.Data::class.java
            )
        }

        /**
         * GETTER FOR SESSION DATA
         */
        val isLoggedIn: Boolean
            get() = getBoolean(Constants.IS_LOGGED_IN)


        /**
         * DESTROY SESSION OR LOGOUT
         */
        fun destroySession() {
            editor!!.clear()
            editor!!.commit()
            editor!!.apply()
        }

        /**
         * SAVE VALUE IN SESSION
         */
        fun saveInt(key: String, value: Int): Int {
            editor!!.putInt(key, value)
            editor!!.commit()
            editor!!.apply()
            return value
        }

        private fun saveString(key: String, value: String): String {
            editor!!.putString(key, value)
            editor!!.commit()
            editor!!.apply()
            return value
        }

        private fun saveObject(key: String, value: Any): Any {
            editor!!.putString(key, Gson().toJson(value))
            editor!!.commit()
            editor!!.apply()
            return value
        }

        private fun saveBoolean(key: String, value: Boolean): Boolean {
            editor!!.putBoolean(key, value)
            editor!!.commit()
            editor!!.apply()
            return value
        }

        /**
         * GET VALUE IN SESSION
         */
        private fun getBoolean(key: String): Boolean {
            return preferences!!.getBoolean(key, false)
        }

        fun getInt(key: String): Int {
            return preferences!!.getInt(key, 0)
        }

        private fun getString(key: String): String? {
            return preferences!!.getString(key, "")
        }

        private lateinit var instance: SessionManager

        @Synchronized
        fun getInstance(): SessionManager = instance


        fun setEmailForCreate(email: String): String {
            return saveString(Constants.QBEMAIL, email)
        }

        fun getEmailForCreate() = getString(Constants.QBEMAIL)
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        dbHelper = DbHelper(this)
        Hawk.init(this).build()
        initFabric()
        checkCredentials()
        initCredentials()
        context = baseContext
        if (BuildConfig.DEBUG)
            Timber.plant(Timber.DebugTree())
        if (auth == null) {
            auth = SessionManager()
            preferences = context!!.getSharedPreferences("FIT_CRU", MODE_PRIVATE)
            editor = preferences!!.edit()
        }
    }

    private fun initFabric() {
        if (!com.quickblox.BuildConfig.DEBUG) {
            //Fabric.with(this, Crashlytics())
        }
    }

    private fun checkCredentials() {
        if (APPLICATION_ID.isEmpty() || AUTH_KEY.isEmpty() || AUTH_SECRET.isEmpty() || ACCOUNT_KEY.isEmpty()) {
            throw AssertionError(getString(R.string.error_qb_credentials_empty))
        }
    }

    private fun initCredentials() {
        QBSettings.getInstance().init(
            applicationContext,
            APPLICATION_ID,
            AUTH_KEY,
            AUTH_SECRET
        )
        QBSettings.getInstance().accountKey = ACCOUNT_KEY
    }

    @Synchronized
    fun getDbHelper(): DbHelper {
        return dbHelper
    }

}
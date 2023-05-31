package com.ennovations.fitcrucoach.quickbox.services.fcm

import android.util.Log
import com.ennovations.fitcrucoach.quickbox.services.LoginService
import com.ennovations.fitcrucoach.quickbox.utils.SharedPrefsHelper
import com.google.firebase.messaging.RemoteMessage
import com.quickblox.messages.services.fcm.QBFcmPushListenerService
import com.quickblox.users.model.QBUser

class PushListenerService : QBFcmPushListenerService() {
    private val TAG = PushListenerService::class.java.simpleName

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        if (SharedPrefsHelper.hasQbUser()) {
            val user: QBUser = SharedPrefsHelper.getQbUser()
            Log.d(TAG, "App has logged user" + user.id)
            LoginService.loginToChatAndInitRTCClient(this, user)
        }
    }

    override fun sendPushMessage(data: MutableMap<Any?, Any?>?, from: String?, message: String?) {
        super.sendPushMessage(data, from, message)
        Log.v(TAG, "From: $from")
        Log.v(TAG, "Message: $message")
    }
}
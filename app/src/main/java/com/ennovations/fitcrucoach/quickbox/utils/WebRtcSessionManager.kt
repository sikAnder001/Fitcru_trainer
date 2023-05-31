package com.ennovations.fitcrucoach.quickbox.utils

import android.util.Log
import com.ennovations.fitcrucoach.activities.CallActivity
import com.ennovations.fitcrucoach.utility.SessionManager

import com.quickblox.videochat.webrtc.QBRTCSession
import com.quickblox.videochat.webrtc.callbacks.QBRTCClientSessionCallbacksImpl

object WebRtcSessionManager : QBRTCClientSessionCallbacksImpl() {
    private val TAG = WebRtcSessionManager::class.java.simpleName

    private var currentSession: QBRTCSession? = null

    fun getCurrentSession(): QBRTCSession? {
        return currentSession
    }

    fun setCurrentSession(qbCurrentSession: QBRTCSession?) {
        currentSession = qbCurrentSession
    }

    override fun onReceiveNewSession(session: QBRTCSession) {
        Log.d(TAG, "onReceiveNewSession to WebRtcSessionManager")

        if (currentSession == null) {
            setCurrentSession(session)
            CallActivity.start(SessionManager.getInstance(), true)
        }
    }

    override fun onSessionClosed(session: QBRTCSession?) {
        Log.d(TAG, "onSessionClosed WebRtcSessionManager")

        if (session == getCurrentSession()) {
            setCurrentSession(null)
        }
    }
}
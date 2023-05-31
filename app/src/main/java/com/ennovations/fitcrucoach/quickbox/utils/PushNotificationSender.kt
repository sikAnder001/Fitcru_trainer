package com.ennovations.fitcrucoach.quickbox.utils

import android.util.Log
import com.quickblox.core.helper.StringifyArrayList
import com.quickblox.messages.QBPushNotifications
import com.quickblox.messages.model.QBEnvironment
import com.quickblox.messages.model.QBEvent
import com.quickblox.messages.model.QBNotificationType
import org.json.JSONException
import org.json.JSONObject

fun sendPushMessage(
    userId: Int, senderName: String, sessionId: String,
    opponentIds: String, opponentNames: String, isVideoCall: Boolean
) {
    // val outMessage =String.format(R.string.push_notification_message.toString())


    // send Push: create QuickBlox Push Notification Event
    val qbEvent = QBEvent()
    val outMessage = "Coach is calling you..."



    qbEvent.notificationType = QBNotificationType.PUSH
    qbEvent.environment = QBEnvironment.DEVELOPMENT

    // generic push - will be delivered to all platforms (Android, iOS, WP, Blackberry..)

    val json = JSONObject()
    try {
        json.put("message", outMessage)
        json.put("ios_voip", "1")
        json.put("VOIPCall", "1")
        json.put("sessionID", sessionId)
        json.put("opponentsIDs", opponentIds)
        json.put("contactIdentifier", opponentNames)
        json.put("conferenceType", if (isVideoCall) "1" else "2")
        json.put("timestamp", System.currentTimeMillis().toString())
    } catch (e: JSONException) {
        e.printStackTrace()
    }

    qbEvent.message = json.toString()
    Log.v("newpush", outMessage)


/*    val customData = HashMap<String, Any>()
    customData["data.message"] = "Hello QuickBlox"
    customData["data.type"] = "First QuickBlox Push"

    Hawk.put(CMESSAGE,customData)
  Log.v("pushNoti",outMessage)*/


    val userIds = StringifyArrayList<Int>()
    userIds.add(userId)
    qbEvent.userIds = userIds


    QBPushNotifications.createEvent(qbEvent).performAsync(null)
}
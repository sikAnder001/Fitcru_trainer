package com.ennovations.fitcrucoach.response

import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.google.gson.annotations.SerializedName
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

data class UserNameAndTimeSlotResponse(
    val error_code: Int,
    val message: String,
    val data: List<SlotList>? = null,
    val on_leave: List<LeaveList>? = null,
    val user: List<UserNameData>? = null
) {
    data class SlotList(
        val coachTimeSlotId: Int,
        val slotTime: String? = null,
        val book_date: String? = null,
        val duration: String? = null,
        @SerializedName("break")
        val break1: String? = null,
    ) {
        override fun toString(): String {
            return CalendarUtils.timeFormatFit(slotTime) ?: ""
        }

        private fun demo(time: String?): String {
            return try {
                val fmt = SimpleDateFormat("hh:mm");
                var date: Date? = null;
                date = fmt.parse(time);
                var formattedTime =
                    SimpleDateFormat("yyyy-MM-dd,hh:mm a", Locale.ENGLISH).format(date)
                formattedTime = formattedTime.split(",")[1]

                formattedTime
            } catch (e: ParseException) {
                e.printStackTrace();
                ""
            }
        }
    }

    data class LeaveList(
        val id: Int,
    )

    data class UserNameData(
        val clientId: Int,
        val clientName: String? = null
    ) {
        override fun toString(): String {
            return clientName ?: ""
        }
    }

}
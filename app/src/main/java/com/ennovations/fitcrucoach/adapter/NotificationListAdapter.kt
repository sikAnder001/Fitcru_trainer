package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.NotificationListItemBinding
import com.ennovations.fitcrucoach.response.NotificationListResponse
import java.time.Instant
import java.time.OffsetDateTime
import java.util.*


class NotificationListAdapter(
    val context: Context?,
    val listener: ReadNotificationItem
) : RecyclerView.Adapter<NotificationListAdapter.ViewHolder>() {

    private var list = listOf<NotificationListResponse.Data>()

    inner class ViewHolder(val binding: NotificationListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(data: NotificationListResponse.Data) {
            binding.apply {
                /*  Glide.with(context!!)
                      .load(data.image_url)
                      .placeholder(R.drawable.place_holder)
                      .into(imageView)*/

                notificationTitle.text = data.type
                notificationMessage.text = data.data
//                timeDuration.text="35 minutes ago"

                var odt: OffsetDateTime = OffsetDateTime.parse(data.created_at)
                var instant: Instant = odt.toInstant()

                var dateO: Date = Date.from(instant)
                var dateC = Date()
                var difference_In_Time = dateC.time - dateO.time

                Log.v("difference", (dateC.time - dateO.time).toString())

                var difference_In_Minutes: Long = ((difference_In_Time / (1000 * 60)) % 60)

                var difference_In_Hours: Long = ((difference_In_Time / (1000 * 60 * 60)) % 24)

                var difference_In_Years: Long = (difference_In_Time / (1000L * 60 * 60 * 24 * 365))

                var difference_In_Days: Long = ((difference_In_Time / (1000 * 60 * 60 * 24)) % 365)

                Log.v(
                    "differencedays",
                    "days" + difference_In_Days + ",minutes" + difference_In_Minutes + ",hours" + difference_In_Hours + ",years" + difference_In_Years + ","
                )

                if (difference_In_Years >= 1) timeDuration.text =
                    "${difference_In_Years} Years ago"
                else if (difference_In_Days >= 1) timeDuration.text =
                    "${difference_In_Days} Days ago"
                else if (difference_In_Hours >= 2) timeDuration.text =
                    "${difference_In_Hours} Hours ago"
                else if (difference_In_Hours >= 1) timeDuration.text =
                    "${difference_In_Hours} Hour ago"
                else if (difference_In_Minutes >= 1) timeDuration.text =
                    "${difference_In_Minutes} Minutes ago"
                else timeDuration.text = "Now"

                if (data.coach_is_read == "1") becky.setBackgroundResource(R.drawable.round_background_light)
                else becky.setBackgroundResource(R.drawable.round_back_transparent)

                itemView.setOnClickListener {
                    becky.setBackgroundResource(R.drawable.round_background_light)
                    if (data.coach_is_read == "0") {
                        listener.onClick(data.id)
                    }
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): NotificationListAdapter.ViewHolder {
        return ViewHolder(
            NotificationListItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    fun setList(list: List<NotificationListResponse.Data>?) {
        this.list = list!!
        notifyDataSetChanged()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: NotificationListAdapter.ViewHolder, position: Int) =
        holder.bind(list[position])

    override fun getItemCount(): Int {
        return try {
            list.size
        } catch (e: Exception) {
            0
        }
    }

    interface ReadNotificationItem {
        fun onClick(
            id: Int
        )
    }


}

package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ennovations.fitcrucoach.Interface.AudioCallInterface
import com.ennovations.fitcrucoach.Interface.CancelAppointmentInterface
import com.ennovations.fitcrucoach.Interface.VideoCallInterface
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.databinding.UpcomingAppointmentRvItemBinding
import com.ennovations.fitcrucoach.quickbox.utils.shortToast
import com.ennovations.fitcrucoach.response.AppointmentListResponse
import com.ennovations.fitcrucoach.utility.CNAME
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.orhanobut.hawk.Hawk
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class UpcomingAppointmentAdapter(

    private val context: Context?, var listener1: AudioCallInterface,
    var listener2: VideoCallInterface

) : RecyclerView.Adapter<UpcomingAppointmentAdapter.ViewHolder>() {

    private var list = listOf<AppointmentListResponse.CoachAppointmentData>()

    lateinit var listener: CancelAppointmentInterface


    inner class ViewHolder(appointmentBinding: UpcomingAppointmentRvItemBinding) :
        RecyclerView.ViewHolder(appointmentBinding.root) {

        var appointmentBinding = appointmentBinding

        @RequiresApi(Build.VERSION_CODES.N)
        fun bind(list: AppointmentListResponse.CoachAppointmentData, position: Int) {

            appointmentBinding.apply {

                Glide.with(context!!).load(list.appointment_detail.user_detail.image_url)
                    .placeholder(
                        context.resources.getDrawable(
                            R.drawable.place_holder,
                            null
                        )
                    )
                    .into(trainerImage)
                trainerName.text = list.appointment_detail.user_detail.name
                Hawk.put(CNAME, list.appointment_detail.user_detail.name)
                Log.v("cname", list.appointment_detail.user_detail.name)


                val date = CalendarUtils.dateFormatFit(list.appointment_detail.appointment_date)
                dateTv.text = date

                val time =
                    if (list.slot_start_time != null) CalendarUtils.timeFormatFit(list.slot_start_time) else CalendarUtils.timeFormatFit(
                        list.slot_strdy_strt_time
                    )
                appTimeTv.text = time

                cancelAppoint.setOnClickListener {

                    listener.onCancelClick(
                        list.appointment_detail.user_id,
                        list.appointment_detail.coach_time_slot_id,
                        list.appointment_detail.id
                    )
                }
                showNote.setOnClickListener {
                    visibility(showNote, noteContainer)
                }
                note1.text = list.appointment_detail.note



                audioCall.setOnClickListener {
                    if (list.appointment_detail.user_detail.quick_blox_id == null) {
                        shortToast("client need to login again")
                    } else {
                        listener1.onAudiocallClick(
                            list.appointment_detail.user_detail.quick_blox_id.toInt()
                        )
                    }


                }

                videoCall.setOnClickListener {
                    if (list.appointment_detail.user_detail.quick_blox_id == null) {
                        shortToast("client need to login again")
                    } else {
                        listener2.onVideocallClick(
                            list.appointment_detail.user_detail.quick_blox_id.toInt()
                        )
                    }

                }
            }

        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingAppointmentAdapter.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        val appointmentBinding = UpcomingAppointmentRvItemBinding.inflate(inflater, parent, false)

        return ViewHolder(appointmentBinding)
    }

    fun setList(list: List<AppointmentListResponse.CoachAppointmentData>) {
        this.list = list
        notifyDataSetChanged()
    }

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onBindViewHolder(holder: UpcomingAppointmentAdapter.ViewHolder, position: Int) {
        holder.bind(list[position], position)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickInterface(listener: CancelAppointmentInterface) {

        this.listener = listener
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun importFormat(date: String?): String {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
        val dateTime = simpleDateFormat.parse(date)
        val simpleDateFormat1 = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            SimpleDateFormat("yyyy-MM-dd")/*,hh:mm:ss*/
        } else {
            TODO("VERSION.SDK_INT < N")
        }
        val date = simpleDateFormat1.format(dateTime)

        return date
    }

    private fun demo(time: String?): String {
        val fmt = SimpleDateFormat("hh:mm");
        var date: Date? = null;
        try {
            date = fmt.parse(time);
        } catch (e: ParseException) {
            e.printStackTrace();
        }
        var formattedTime = SimpleDateFormat("yyyy-MM-dd,hh:mm a", Locale.ENGLISH).format(date)
        formattedTime = formattedTime.split(",")[1]

        return formattedTime
    }

    private fun visibility(textView: TextView, linearLayout: LinearLayout) {
        linearLayout.visibility =
            if (linearLayout.visibility == View.GONE) View.VISIBLE else View.GONE
        textView.setCompoundDrawablesWithIntrinsicBounds(
            0,
            0,
            if (linearLayout.visibility == View.GONE) R.drawable.ic_arrow_down else R.drawable.ic_arrow_up,
            0
        )
    }

}
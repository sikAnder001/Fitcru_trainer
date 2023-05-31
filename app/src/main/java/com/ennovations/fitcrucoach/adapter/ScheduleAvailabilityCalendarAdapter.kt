package com.ennovations.fitcrucoach.adapter

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.calenderholder.ScheduleAvailabilityViewHolder
import com.ennovations.fitcrucoach.fragments.ScheduleAvailabilityFragment
import com.ennovations.fitcrucoach.utility.CalendarUtils
import java.time.LocalDate

class ScheduleAvailabilityCalendarAdapter(
    days: List<LocalDate>,
    year: List<LocalDate>,
    onItemListener: ScheduleAvailabilityFragment,
    val context: Context,
    val listenerShow: ShowCheckBox,
    val listenerHide: OnMarkItemListener
) :
    RecyclerView.Adapter<ScheduleAvailabilityViewHolder>() {

    private val days: List<LocalDate>
    private val year: List<LocalDate>
    private val onItemListener: OnItemListener

    private var list = listOf<String>()
    private var listAppoint = listOf<String>()

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScheduleAvailabilityViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.context)
        val view: View =
            inflater.inflate(R.layout.schedule_availability_calender_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.layoutParams
        return ScheduleAvailabilityViewHolder(view, onItemListener, days)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: ScheduleAvailabilityViewHolder, position: Int) {
        val date: LocalDate = days[position]
        if (date == null) {
            holder.dayOfMonth.text = ""
            // remove views which are not visible
            holder.dayOfMonth.visibility = View.GONE
            holder.parentView.visibility = View.GONE
        } else {
            holder.dayOfMonth.visibility = View.VISIBLE
            holder.parentView.visibility = View.VISIBLE
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            if (date == CalendarUtils.selectedDate) {
                holder.dayOfMonth.setTextColor(Color.BLACK)
                holder.parentView.setBackgroundResource(R.drawable.date_selected)
                for (i in listAppoint) {
                    if (i == CalendarUtils.selectedDate.toString()) {
                        listenerHide.onMarkItemClick(1)
                        break
                    } else {
                        listenerHide.onMarkItemClick(0)
                    }
                }
                for (i in list) {
                    if (i == CalendarUtils.selectedDate.toString()) {
                        listenerShow.onItemClick(1)
                        break
                    } else listenerShow.onItemClick(0)
                }
            } else {
                holder.parentView.setBackgroundResource(0)
                holder.dayOfMonth.setTextColor(Color.GRAY)
                for (i in list) {
                    if (i == date.toString()) {
                        holder.dayOfMonth.setTextColor(Color.WHITE)
                        holder.parentView.setBackgroundResource(R.drawable.date_marked_)
                    }
                }
            }
        }
    }


    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate?)
    }

    interface OnMarkItemListener {
        fun onMarkItemClick(position: Int)
    }

    init {
        this.days = days
        this.year = year
        this.onItemListener = onItemListener
    }

    fun setList(list: List<String>, listAppoint: List<String>) {
        this.list = list
        this.listAppoint = listAppoint
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return days.size
    }

    interface ShowCheckBox {
        fun onItemClick(position: Int)
    }
}
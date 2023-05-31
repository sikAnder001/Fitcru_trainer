package com.ennovations.fitcrucoach.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.fragments.TAppointmentFragment
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrunewandroid.holder.CalendarViewHolder
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class CalendarAdapter(
    days: List<LocalDate>,
    year: List<LocalDate>,
    onItemListener: TAppointmentFragment
) :
    RecyclerView.Adapter<CalendarViewHolder>() {

    private val days: List<LocalDate>
    private val year: List<LocalDate>
    private val onItemListener: OnItemListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.context)

        val view: View = inflater.inflate(R.layout.calender_cell, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.layoutParams

        return CalendarViewHolder(view, onItemListener, days)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        val date: LocalDate = days[position]
        /*val day1 = "${days[position]}" //TODO code For hide the previous date
        var day2 = LocalDate.now()
        val yesterday = "${day2.minusDays(1)}"
        val parse1: Date = SimpleDateFormat("yyyy-MM-dd").parse(day1)
        val parse2: Date = SimpleDateFormat("yyyy-MM-dd").parse(yesterday)
        val cmp = parse1.compareTo(parse2)
        if (cmp <= 0) {
            holder.apply {
                cal.visibility = View.GONE

                dayOfMonth.visibility = View.GONE

                weekDay.visibility = View.GONE

                parentView.visibility = View.GONE
            }
        } else*/ if (date == null) {
            // remove views which are not visible
            holder.dayOfMonth.text = ""
            holder.apply {

                cal.visibility = View.GONE

                dayOfMonth.visibility = View.GONE

                weekDay.visibility = View.GONE

                parentView.visibility = View.GONE
            }
        } else {
            holder.apply {
                cal.visibility = View.VISIBLE

                dayOfMonth.visibility = View.VISIBLE

                weekDay.visibility = View.VISIBLE

                parentView.visibility = View.VISIBLE

                dayOfMonth.text = date.dayOfMonth.toString()

                weekDay.text = date.dayOfWeek.getDisplayName(
                    TextStyle.SHORT,
                    Locale.forLanguageTag("en")
                ).toString().substring(0, 3)
            }
            if (date == CalendarUtils.selectedDate) {
                holder.apply {

                    dayOfMonth.setTextColor(Color.BLACK)

                    weekDay.setTextColor(Color.BLACK)

                    parentView.setBackgroundResource(R.drawable.date_selected)
                }
            } else {
                holder.apply {
                    parentView.setBackgroundResource(0)
                    dayOfMonth.setTextColor(Color.WHITE)

                    weekDay.setTextColor(Color.WHITE)

                    parentView.setBackgroundResource(R.drawable.back_cal)
                }

            }
        }
    }


    interface OnItemListener {
        fun onItemClick(position: Int, date: LocalDate?)
    }

    init {
        this.days = days
        this.year = year
        this.onItemListener = onItemListener
    }

    override fun getItemCount(): Int {
        return days.size
    }
}
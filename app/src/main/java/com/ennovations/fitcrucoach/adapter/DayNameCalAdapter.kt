package com.ennovations.fitcrucoach.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.fragments.ScheduleAvailabilityFragment
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrunewandroid.holder.DayNameCalViewHolder
import java.time.LocalDate

class DayNameCalAdapter(
    days: List<LocalDate>,
    year: List<LocalDate>,
    onItemListener: ScheduleAvailabilityFragment
) :
    RecyclerView.Adapter<DayNameCalViewHolder>() {

    private val days: List<LocalDate>
    private val year: List<LocalDate>
    private val onItemListener: OnItemListener
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayNameCalViewHolder {

        val inflater: LayoutInflater = LayoutInflater.from(parent.getContext())

        val view: View = inflater.inflate(R.layout.day_name_cal_rv_item, parent, false)

        val layoutParams: ViewGroup.LayoutParams = view.getLayoutParams()

        return DayNameCalViewHolder(view, onItemListener, days)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: DayNameCalViewHolder, position: Int) {
        val date: LocalDate = days[position]
        if (date == null) {
//            holder.dayOfMonth.setText("")
            // remove views which are not visible
            holder.apply {

                cal.setVisibility(View.GONE)

//                dayOfMonth.setVisibility(View.GONE)

                weekDay.setVisibility(View.GONE)

                parentView.setVisibility(View.GONE)
            }
        } else {
            holder.apply {
                cal.setVisibility(View.VISIBLE)

//                dayOfMonth.setVisibility(View.VISIBLE)

                weekDay.setVisibility(View.VISIBLE)

                parentView.setVisibility(View.VISIBLE)

//                dayOfMonth.setText(date.getDayOfMonth().toString())

                weekDay.setText(date.getDayOfWeek().toString())
            }
            if (date == CalendarUtils.selectedDate) {
                holder.apply {

//                    dayOfMonth.setTextColor(Color.BLACK)

                    weekDay.setTextColor(Color.BLACK)

                    parentView.setBackgroundResource(R.drawable.date_selected)
                }
            } else {
                holder.apply {
                    parentView.setBackgroundResource(0)
//                    dayOfMonth.setTextColor(Color.WHITE)

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
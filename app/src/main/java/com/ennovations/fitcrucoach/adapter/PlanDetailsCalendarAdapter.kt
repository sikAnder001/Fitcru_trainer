package com.ennovations.fitcrucoach.adapter

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.fragments.PlanDetailsFragment
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrunewandroid.holder.PlanDetailsCalendarViewHolder
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*

class PlanDetailsCalendarAdapter(
    days: List<LocalDate>,
    year: List<LocalDate>,
    onItemListener: PlanDetailsFragment
) :
    RecyclerView.Adapter<PlanDetailsCalendarViewHolder>() {

    private val days: List<LocalDate>
    private val year: List<LocalDate>
    private val onItemListener: OnItemListener
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): PlanDetailsCalendarViewHolder {
        val inflater: LayoutInflater = LayoutInflater.from(parent.getContext())
        val view: View = inflater.inflate(R.layout.plan_detail_calendar_cell, parent, false)
        val layoutParams: ViewGroup.LayoutParams = view.getLayoutParams()
        return PlanDetailsCalendarViewHolder(view, onItemListener, days)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: PlanDetailsCalendarViewHolder, position: Int) {
        val date: LocalDate = days[position]
        if (date == null) {
            holder.dayOfMonth.text = ""
            // remove views which are not visible
            holder.cal.visibility = View.GONE
            holder.dayOfMonth.visibility = View.GONE
            holder.weekDay.visibility = View.GONE
            holder.parentView.visibility = View.GONE
        } else {
            holder.cal.visibility = View.VISIBLE
            holder.dayOfMonth.visibility = View.VISIBLE
            holder.weekDay.visibility = View.VISIBLE
            holder.parentView.visibility = View.VISIBLE
            holder.dayOfMonth.text = date.dayOfMonth.toString()
            holder.weekDay.text = date.dayOfWeek.getDisplayName(
                TextStyle.SHORT,
                Locale.forLanguageTag("en")
            )
            if (date == CalendarUtils.selectedDate) {
                holder.dayOfMonth.setTextColor(Color.BLACK)
                holder.weekDay.setTextColor(Color.BLACK)
                holder.parentView.setBackgroundResource(R.drawable.date_selected)
            } else {
                holder.parentView.setBackgroundResource(0)
                holder.dayOfMonth.setTextColor(Color.WHITE)
                holder.weekDay.setTextColor(Color.WHITE)
                holder.parentView.setBackgroundResource(R.drawable.back_cal)
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
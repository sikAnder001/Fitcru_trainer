package com.ennovations.fitcrucoach.calenderholder

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.ScheduleAvailabilityCalendarAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class ScheduleAvailabilityViewHolder internal constructor(
    itemView: View,
    onItemListener: ScheduleAvailabilityCalendarAdapter.OnItemListener,
    days: List<LocalDate>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val days: List<LocalDate>
    var parentView: ConstraintLayout
    var dayOfMonth: TextView
    private val onItemListener: ScheduleAvailabilityCalendarAdapter.OnItemListener

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View) {
        try {
            val d1 = "${days[adapterPosition]}"
            val d2 = "${LocalDate.now()}"
            val parse1: Date = SimpleDateFormat("yyyy-MM-dd").parse(d1)
            val parse2: Date = SimpleDateFormat("yyyy-MM-dd").parse(d2)
            val cmp = parse1.compareTo(parse2)
            if (cmp >= 0)
                onItemListener.onItemClick(adapterPosition, days[adapterPosition])
        } catch (e: Exception) {
        }
    }

    init {
        parentView = itemView.findViewById(R.id.parentView)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
        this.days = days
    }
}
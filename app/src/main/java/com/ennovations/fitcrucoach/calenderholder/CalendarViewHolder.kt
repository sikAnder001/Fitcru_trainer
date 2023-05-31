package com.ennovations.fitcrunewandroid.holder

import android.os.Build
import android.view.View
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.CalendarAdapter
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*

class CalendarViewHolder internal constructor(
    itemView: View,
    onItemListener: CalendarAdapter.OnItemListener,
    days: List<LocalDate>
) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
    private val days: List<LocalDate>
    var parentView: ConstraintLayout
    var dayOfMonth: TextView
    var weekDay: TextView
    var cal: LinearLayoutCompat
    private val onItemListener: CalendarAdapter.OnItemListener

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onClick(view: View) {

        val day1 = "${days[adapterPosition]}"
        val day2 = "${LocalDate.now()}"
        val parse1: Date = SimpleDateFormat("yyyy-MM-dd").parse(day1)
        val parse2: Date = SimpleDateFormat("yyyy-MM-dd").parse(day2)
        val cmp = parse1.compareTo(parse2)
        if (cmp >= 0)
            onItemListener.onItemClick(adapterPosition, days[adapterPosition])
    }


    init {
        parentView = itemView.findViewById(R.id.parentView)
        dayOfMonth = itemView.findViewById(R.id.cellDayText)
        weekDay = itemView.findViewById(R.id.weekDay)
        cal = itemView.findViewById(R.id.cal)
        this.onItemListener = onItemListener
        itemView.setOnClickListener(this)
        this.days = days
    }
}
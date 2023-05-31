package com.ennovations.fitcrucoach.utility

import android.os.Build
import androidx.annotation.RequiresApi
import java.text.ParseException
import java.text.SimpleDateFormat
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeFormatterBuilder
import java.time.format.TextStyle
import java.util.*

object CalendarUtils {
    var selectedDate: LocalDate? = null

    @RequiresApi(Build.VERSION_CODES.O)
    fun getWeekDayName(s: String?): String {
        val dtfInput = DateTimeFormatter.ofPattern("dd MMMM yyyy", Locale.ENGLISH)
        return LocalDate.parse(s, dtfInput).dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedDate(date: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("dd MMMM yyyy")
        return date.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formattedDate1(date: LocalDate): String {
        val dTF: DateTimeFormatter = DateTimeFormatterBuilder().parseCaseInsensitive()
            .appendPattern("yyyy-MM-dd")
            .toFormatter(Locale.getDefault())
        return date.format(dTF)
    }

    fun formattedTime(time: LocalTime): String {
        val formatter = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            DateTimeFormatter.ofPattern("hh:mm:ss a")
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        return time.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthFromDate(month: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMMM")
        return month.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthFromDateShort(month: LocalDate): String {
        val formatter = DateTimeFormatter.ofPattern("MMM") //for month full name use "MMMM"
        return month.format(formatter)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun yearFromDate(year: LocalDate): String {
        val formatterYear = DateTimeFormatter.ofPattern("yyyy")
        return year.format(formatterYear)
    }

    fun daysInMonthArray(date: LocalDate?): ArrayList<LocalDate?> {
        val daysInMonthArray = ArrayList<LocalDate?>()
        val yearMonth = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            YearMonth.from(date)
        } else {
            TODO("VERSION.SDK_INT < O")
        }
        val daysInMonth = yearMonth.lengthOfMonth()
        val firstOfMonth = selectedDate!!.withDayOfMonth(1)
        val dayOfWeek = firstOfMonth.dayOfWeek.value
        for (i in 1..42) {
            if (i <= dayOfWeek || i > daysInMonth + dayOfWeek) daysInMonthArray.add(null) else daysInMonthArray.add(
                LocalDate.of(
                    selectedDate!!.year, selectedDate!!.month, i - dayOfWeek
                )
            )
        }
        return daysInMonthArray
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysInWeekArray(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = sundayForDate(selectedDate)
        val endDate = current!!.plusWeeks(1)
        while (current!!.isBefore(endDate)) {
            days.add(current)
            current = current.plusDays(1)
        }
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun currentDate(): LocalDate? {
        val date = LocalDate.now()
        return date
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun sevenDaysFun(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = selectedDate.minusDays(7)
        val endDate = current!!.plusWeeks(1)
        for (i in 1..15) {
            days.add(current)
            current = current!!.plusDays(1)
        }
        return days
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun sundayForDate(current: LocalDate): LocalDate? {
        var current = current
        val oneWeekAgo = current.minusWeeks(1)
        while (current.isAfter(oneWeekAgo)) {
            if (current.dayOfWeek == DayOfWeek.SUNDAY) return current
            current = current.minusDays(1)
        }
        return null
    }

    var formatDate = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        SimpleDateFormat("dd MMMM YYYY", Locale.US)
        SimpleDateFormat("dd MMMM YYYY", Locale.US)
    } else {
        TODO("VERSION.SDK_INT < N")
    }

    fun dateFormatFit(date: String): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            val dateTime = simpleDateFormat.parse(date)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("dd MMM yyyy").format(dateTime)
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    fun timeFormatFit(time: String?): String {
        var formattedTime: String? = null
        val fmt = SimpleDateFormat("hh:mm");
        var date: Date? = null;
        if (time != null) {
            try {
                date = fmt.parse(time ?: "");
            } catch (e: ParseException) {
                e.printStackTrace();
            }
            formattedTime = SimpleDateFormat("yyyy-MM-dd,hh:mm a", Locale.ENGLISH).format(date)
            formattedTime = formattedTime.split(",")[1]
        } else formattedTime = ""

        return formattedTime ?: ""
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun dateFormatForApi(join: String?): String {
        return try {
            val simpleDateFormat = SimpleDateFormat("dd MMM yyyy")
            val dateTime = simpleDateFormat.parse(join)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                SimpleDateFormat("yyyy-MM-dd").format(dateTime)
            } else {
                ""
            }
        } catch (e: Exception) {
            ""
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun perfectDate(selectedDate: LocalDate): ArrayList<LocalDate?> {
        val days = ArrayList<LocalDate?>()
        var current = LocalDate.now()
        val endDate = current!!.plusWeeks(1)
        for (i in 1..15) {
            days.add(current)
            current = current!!.plusDays(1)
        }
        return days
    }
}
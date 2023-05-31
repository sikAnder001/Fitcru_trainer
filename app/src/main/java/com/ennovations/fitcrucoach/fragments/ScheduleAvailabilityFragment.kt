package com.ennovations.fitcrucoach.fragments

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.DayNameCalAdapter
import com.ennovations.fitcrucoach.adapter.ScheduleAvailabilityCalendarAdapter
import com.ennovations.fitcrucoach.adapter.ScheduleAvailabilityCalendarAdapter.ShowCheckBox
import com.ennovations.fitcrucoach.databinding.FragmentScheduleAvailabilityBinding
import com.ennovations.fitcrucoach.helper.TimePickerHelper
import com.ennovations.fitcrucoach.response.AppointmentListResponse
import com.ennovations.fitcrucoach.response.GetUnmarkedResponse
import com.ennovations.fitcrucoach.response.MarkUnavailableResponse
import com.ennovations.fitcrucoach.response.RemoveUnavailabilityDate
import com.ennovations.fitcrucoach.utility.CalendarUtils
import com.ennovations.fitcrucoach.utility.CalendarUtils.selectedDate
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.AppointmentViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
class ScheduleAvailabilityFragment : Fragment(),
    ScheduleAvailabilityCalendarAdapter.OnItemListener, DayNameCalAdapter.OnItemListener {

    private lateinit var scheduleBinding: FragmentScheduleAvailabilityBinding

    private lateinit var scheduleCalendarAdapter: ScheduleAvailabilityCalendarAdapter

    private val viewModel by viewModels<AppointmentViewModel>()

    private lateinit var dayNameCalAdapter: DayNameCalAdapter

    private lateinit var timePicker: TimePickerHelper

    private lateinit var loading: CustomProgressLoading

    private var date = ""

    @Inject
    lateinit var tokenManager: TokenManager

    var timeSet: Int = 0

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        scheduleBinding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_schedule_availability,
            container,
            false
        )

        loading = CustomProgressLoading(requireContext())

        listeners()

        timePicker = TimePickerHelper(requireContext(), false, true)

        CalendarUtils.selectedDate = LocalDate.now()

        setMonthView()

        afterHittingPreNextBtn()

        scheduleBinding.savePopUp.setOnClickListener {
            val dialogBuilder =
                AlertDialog.Builder(requireActivity(), R.style.AlertDialogStyle1)

            dialogBuilder.setMessage("To continue, kindly cancel the appointments made for this day.")
                .setCancelable(false)
                .setPositiveButton("Ok", DialogInterface.OnClickListener { _, _ ->

                })

            val alert = dialogBuilder.create()
            alert.setTitle("Alert!")
            alert.show()
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now()
            this.setMonthView()
        }

        getMarkedUnavailability()
        hideGhost()

        scheduleBinding.apply {

            markUnavailableForTodayCb.isChecked = false

            saveBtn.setOnClickListener {
                if (date != "") {
                    //date = CalendarUtils.currentDate().toString()
                    viewModel.markUnavailable(date)
                    saveUnavailability()
                } else Util.toast(requireContext(), "Please select date")
            }

        }

        return scheduleBinding.root
    }

    @SuppressLint("NewApi")
    private fun getMarkedUnavailability() {

        viewModel.getMarkedUnavailability()

        viewModel.getMarkUnavailability.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {

                    val response = it.data!! as GetUnmarkedResponse

                    scheduleCalendarAdapter.setList(response.data, response.appointments)

                    scheduleBinding.apply {
                        markUnavailableForTodayCb.setOnCheckedChangeListener { _, _ ->
                            if (markUnavailableForTodayCb.isChecked) {
                                if (date == "") {
                                    date = CalendarUtils.currentDate().toString()
                                    viewModel.removeUnavailabilityDate(date)
                                    removeUnavailabilityDate()
                                    markUnavailableForTodayCb.isChecked = false
                                } else {
                                    viewModel.removeUnavailabilityDate(date)
                                    removeUnavailabilityDate()
                                    markUnavailableForTodayCb.isChecked = false
                                }
                            }
                        }
                    }
                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data!!, GetUnmarkedResponse::class.java)
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun saveUnavailability() {

        viewModel.markUnavailable.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {

                    val response = it.data!! as MarkUnavailableResponse

                    viewModel.getMarkedUnavailability()
                    Util.toast(requireContext(), response.message)

                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data!!, AppointmentListResponse::class.java)
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    private fun removeUnavailabilityDate() {
        viewModel.removeUnavailabilityDate.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {

                    val response = it.data!! as RemoveUnavailabilityDate

                    viewModel.getMarkedUnavailability()
                    Util.toast(requireContext(), response.message)

                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data!!, RemoveUnavailabilityDate::class.java)
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {

            CalendarUtils.selectedDate = date

            val day: String = CalendarUtils.formattedDate(date)

            val weekDay: String = CalendarUtils.getWeekDayName(day)

            this.date = selectedDate.toString()

            if (scheduleCalendarAdapter != null) {

                scheduleCalendarAdapter.notifyDataSetChanged()

            }

            if (dayNameCalAdapter != null) {

                dayNameCalAdapter.notifyDataSetChanged()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {
        scheduleBinding.monthTV.text = selectedDate?.let {
            CalendarUtils.monthFromDate(
                it
            )
        }
        val daysInMonth: List<LocalDate?> =
            CalendarUtils.daysInMonthArray(selectedDate)

        //Toast.makeText(context, "$daysInMonth", Toast.LENGTH_SHORT).show()

        val year: List<LocalDate?> = CalendarUtils.daysInMonthArray(selectedDate)

        scheduleCalendarAdapter = ScheduleAvailabilityCalendarAdapter(
            daysInMonth as List<LocalDate>,
            year as List<LocalDate>, this, requireContext(),
            object : ShowCheckBox {
                override fun onItemClick(position: Int) {
                    if (position == 1) scheduleBinding.markUnavailableForTodayCb.visibility =
                        View.VISIBLE else scheduleBinding.markUnavailableForTodayCb.visibility =
                        View.GONE
                }
            },
            object : ScheduleAvailabilityCalendarAdapter.OnMarkItemListener {
                override fun onMarkItemClick(position: Int) {
                    if (position == 1) {
                        scheduleBinding.saveBtn.visibility =
                            View.GONE
                        scheduleBinding.savePopUp.visibility =
                            View.VISIBLE
                    } else {
                        scheduleBinding.saveBtn.visibility =
                            View.VISIBLE
                        scheduleBinding.savePopUp.visibility =
                            View.GONE
                    }
                }

            }
        )

        val layoutManager: RecyclerView.LayoutManager = GridLayoutManager(requireContext(), 7)

        scheduleBinding.scheduleAvailabilityRv.layoutManager = layoutManager

        scheduleBinding.scheduleAvailabilityRv.adapter = scheduleCalendarAdapter

        //TODO: Top Calendar

        val daysInMonth1: List<LocalDate?> =
            CalendarUtils.sevenDaysFun(CalendarUtils.selectedDate!!)

        val year1: List<LocalDate?> = CalendarUtils.sevenDaysFun(CalendarUtils.selectedDate!!)

        dayNameCalAdapter = DayNameCalAdapter(
            daysInMonth1 as List<LocalDate>,
            year1 as List<LocalDate>, this
        )
        val layoutManager1: RecyclerView.LayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        scheduleBinding!!.dayNameRv.apply {
            setLayoutManager(layoutManager1)
            adapter = dayNameCalAdapter
        }

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun afterHittingPreNextBtn() {
        scheduleBinding.apply {
            preMonth.setOnClickListener {
                var select = CalendarUtils.selectedDate!!.minusMonths(1)
                val d1 = "$select"
                val d2 = "${LocalDate.now()}"
                val parse1: Date = SimpleDateFormat("yyyy-MM-dd").parse(d1)
                val parse2: Date = SimpleDateFormat("yyyy-MM-dd").parse(d2)
                val cmp = parse1.compareTo(parse2)
                if (cmp >= 0) {
                    CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.minusMonths(1)
                } else CalendarUtils.selectedDate = LocalDate.now()
                viewModel.apply {
                    getMarkedUnavailability()
                }
                setMonthView()
            }

            nextMonth.setOnClickListener {
                CalendarUtils.selectedDate = CalendarUtils.selectedDate!!.plusMonths(1)
                getMarkedUnavailability()
                setMonthView()
            }
        }

    }

    private fun listeners() {
        scheduleBinding.apply {

            backBtn.setOnClickListener { requireActivity().onBackPressed() }

            startTimeOneContainer.setOnClickListener {

                timeSet = 0

                showTimePickerDialog()
            }

            endTimeOneContainer.setOnClickListener {

                timeSet = 1

                showTimePickerDialog()
            }

            startTimeTwoContainer.setOnClickListener {

                timeSet = 2

                showTimePickerDialog()
            }

            endTimeTwoContainer.setOnClickListener {

                timeSet = 3

                showTimePickerDialog()
            }

            startTimeThreeContainer.setOnClickListener {

                timeSet = 4

                showTimePickerDialog()
            }

            endTimeThreeContainer.setOnClickListener {

                timeSet = 5

                showTimePickerDialog()
            }

            startTimeOneContainer1.setOnClickListener {

                timeSet = 6

                showTimePickerDialog()
            }

            endTimeOneContainer1.setOnClickListener {

                timeSet = 7

                showTimePickerDialog()
            }

            startTimeTwoContainer1.setOnClickListener {

                timeSet = 8

                showTimePickerDialog()
            }

            endTimeTwoContainer1.setOnClickListener {

                timeSet = 9

                showTimePickerDialog()
            }

            startTimeThreeContainer1.setOnClickListener {

                timeSet = 10

                showTimePickerDialog()
            }

            endTimeThreeContainer1.setOnClickListener {

                timeSet = 11

                showTimePickerDialog()
            }

        }
    }

    private fun showTimePickerDialog() {
        val cal = Calendar.getInstance()
        val h = cal.get(Calendar.HOUR_OF_DAY)
        val m = cal.get(Calendar.MINUTE)
        timePicker.showDialog(h, m, object : TimePickerHelper.Callback {
            override fun onTimeSelected(hourOfDay: Int, minute: Int) {
                val hourStr = if (hourOfDay < 10) "0${hourOfDay}" else "${hourOfDay}"
                val minuteStr = if (minute < 10) "0${minute}" else "${minute}"
                if (timeSet == 0) {
                    scheduleBinding.startTimeOneTv.text = "${hourStr}:${minuteStr}"
                } else if (timeSet == 1) {
                    scheduleBinding.endTimeOneTv.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 2) {
                    scheduleBinding.startTimeTwoTv.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 3) {
                    scheduleBinding.endTimeTwoTv.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 4) {
                    scheduleBinding.startTimeThreeTv.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 5) {
                    scheduleBinding.endTimeThreeTv.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 6) {
                    scheduleBinding.startTimeOneTv1.text = "${hourStr}:${minuteStr}"
                } else if (timeSet == 6) {
                    scheduleBinding.startTimeOneTv1.text = "${hourStr}:${minuteStr}"
                } else if (timeSet == 7) {
                    scheduleBinding.endTimeOneTv1.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 8) {
                    scheduleBinding.startTimeTwoTv1.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 9) {
                    scheduleBinding.endTimeTwoTv1.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 10) {
                    scheduleBinding.startTimeThreeTv1.text = "${hourStr}:${minuteStr}"

                } else if (timeSet == 11) {
                    scheduleBinding.endTimeThreeTv1.text = "${hourStr}:${minuteStr}"
                }

            }
        })
    }

    private fun showView(linearLayout: LinearLayout) {

        linearLayout.visibility =
            if (linearLayout.visibility == View.GONE) {
                View.VISIBLE
            } else {
                View.GONE
            }

    }

    private fun hideGhost() {
        scheduleBinding.apply {
            calenderDateMonthContainer.setOnClickListener {
                showView(ghostContainer)
                showView(anotherScheduleAvailabilityContainer)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        viewModel.apply {
            getMarkedUnavailability()
        }
    }
}
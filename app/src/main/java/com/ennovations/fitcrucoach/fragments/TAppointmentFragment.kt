package com.ennovations.fitcrucoach.fragments

import android.annotation.SuppressLint
import android.app.ActivityManager
import android.app.DatePickerDialog
import android.app.NotificationManager
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.Interface.AudioCallInterface
import com.ennovations.fitcrucoach.Interface.CancelAppointmentInterface
import com.ennovations.fitcrucoach.Interface.VideoCallInterface
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.activities.CallActivity
import com.ennovations.fitcrucoach.activities.OpponentsActivity
import com.ennovations.fitcrucoach.activities.PermissionsActivity
import com.ennovations.fitcrucoach.adapter.CalendarAdapter
import com.ennovations.fitcrucoach.adapter.UpcomingAppointmentAdapter
import com.ennovations.fitcrucoach.body.AddNewAppointmentBody
import com.ennovations.fitcrucoach.body.CancelAppointmentBody
import com.ennovations.fitcrucoach.databinding.AddAppointmentLayoutBinding
import com.ennovations.fitcrucoach.databinding.FragmentTAppointmentBinding
import com.ennovations.fitcrucoach.helper.TimePickerHelper
import com.ennovations.fitcrucoach.quickbox.db.QbUsersDbManager
import com.ennovations.fitcrucoach.quickbox.executor.Executor
import com.ennovations.fitcrucoach.quickbox.executor.ExecutorTask
import com.ennovations.fitcrucoach.quickbox.services.CallService
import com.ennovations.fitcrucoach.quickbox.services.LoginService
import com.ennovations.fitcrucoach.quickbox.utils.*
import com.ennovations.fitcrucoach.response.AddNewAppointmentResponse
import com.ennovations.fitcrucoach.response.AppointmentListResponse
import com.ennovations.fitcrucoach.response.LoginResponse
import com.ennovations.fitcrucoach.response.UserNameAndTimeSlotResponse
import com.ennovations.fitcrucoach.utility.*
import com.ennovations.fitcrucoach.view_model.AppointmentViewModel
import com.ennovations.fitcrucoach.view_model.LoginViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.orhanobut.hawk.Hawk
import com.quickblox.chat.QBChatService
import com.quickblox.core.QBEntityCallback
import com.quickblox.core.exception.QBResponseException
import com.quickblox.core.request.GenericQueryRule
import com.quickblox.core.request.QBPagedRequestBuilder
import com.quickblox.messages.services.QBPushManager
import com.quickblox.messages.services.SubscribeService
import com.quickblox.users.QBUsers
import com.quickblox.users.model.QBUser
import com.quickblox.videochat.webrtc.QBRTCClient
import com.quickblox.videochat.webrtc.QBRTCTypes
import dagger.hilt.android.AndroidEntryPoint
import java.time.LocalDate
import java.util.*
import javax.inject.Inject


private const val PER_PAGE_SIZE_100 = 100
private const val ORDER_RULE = "order"
private const val ORDER_DESC_UPDATED = "desc date updated_at"
private const val TOTAL_PAGES_BUNDLE_PARAM = "total_pages"


@AndroidEntryPoint
class TAppointmentFragment : Fragment(), CalendarAdapter.OnItemListener {
    private val TAG = OpponentsActivity::class.java.simpleName

    private lateinit var usersRecyclerView: RecyclerView
    private lateinit var currentUser: QBUser
    private var progressDialog: ProgressDialog? = null

    private lateinit var loginResponse: LoginResponse


    private var currentPage = 0
    private var isLoading: Boolean = false
    private var hasNextPage: Boolean = true

    private lateinit var appointmentBinding: FragmentTAppointmentBinding

    private lateinit var addNewAppointmentBinding: AddAppointmentLayoutBinding

    private val loginViewModel by viewModels<LoginViewModel>()

    private lateinit var bottomSheetDialog: BottomSheetDialog

    private lateinit var calendarAdapter: CalendarAdapter

    private lateinit var appointmentAdapter: UpcomingAppointmentAdapter

    private val viewModel by viewModels<AppointmentViewModel>()

    private var nameList = ArrayList<UserNameAndTimeSlotResponse.UserNameData>()

    private var nameList1 = ArrayList<String>()

    private var timeList = ArrayList<UserNameAndTimeSlotResponse.SlotList>()

    private var timeList1 = ArrayList<String>()

    private var nameU = 0

    private var timeU = 0

    private var name = 0

    private var time = 0
    private var nameC = String
    private var imageC = String

    private var dateFilter = ""
    private var date = String

    private lateinit var loading: CustomProgressLoading
    private var opponents: ArrayList<QBUser> = ArrayList<QBUser>()

    private lateinit var timePicker: TimePickerHelper

    @Inject
    lateinit var tokenManager: TokenManager

    private var userId1 = 0


    private var coachSlotTimeId1 = 0

    private var appointmentId1 = 0

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        appointmentBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_t_appointment, container, false)

        loading = CustomProgressLoading(requireContext())


        timePicker = TimePickerHelper(requireContext(), false, true)

        try {
            currentUser = SharedPrefsHelper.getCurrentUser()
        } catch (e: Exception) {

        }

        /* Hawk.get(NAME,name)
         Hawk.get(IMAGE,image)*/




        getAppointmentData()



        callBottomSheet()

        getDetailsList()
        checkIsLoggedInChat()

        appointmentBinding.apply {

            var name = SessionManager.getCoachDetails().coach_name
            name = name[0].toString().uppercase()

            backBtn.text = name

            backBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_TAppointmentFragment_to_myProfileFragment)
            }

            calBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_TAppointmentFragment_to_scheduleAvailabilityFragment)
            }
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CalendarUtils.selectedDate = LocalDate.now()
            this.setMonthView()
        }

        return appointmentBinding.root
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun setMonthView() {

        val daysInMonth: List<LocalDate?> =
            CalendarUtils.perfectDate(CalendarUtils.selectedDate!!)

        val year: List<LocalDate?> = CalendarUtils.daysInMonthArray(CalendarUtils.selectedDate)

        calendarAdapter = CalendarAdapter(
            daysInMonth as List<LocalDate>,
            year as List<LocalDate>, this
        )
        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.HORIZONTAL, false
        )
        appointmentBinding!!.calendarRv.apply {
            setLayoutManager(layoutManager)
            adapter = calendarAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onItemClick(position: Int, date: LocalDate?) {
        if (date != null) {
            CalendarUtils.selectedDate = date
            val day: String = CalendarUtils.formattedDate1(date)
            if (calendarAdapter != null) {
                calendarAdapter.notifyDataSetChanged()
            }
            if (day != "") {
                dateFilter = day
                viewModel.getAppointmentListFilter(dateFilter)
            }
        }
    }

    private fun getAppointmentData() {
        val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        appointmentBinding.upcomingAppointmentRv.layoutManager = linearLayout

        appointmentBinding.upcomingAppointmentRv.setHasFixedSize(true)

        appointmentAdapter =
            UpcomingAppointmentAdapter(context, listener1 = object : AudioCallInterface {
                override fun onAudiocallClick(quickBloxId: Int) {
                    // if (checkIsLoggedInChat()) {


                    if (opponents.filter { it.id == quickBloxId }.isNullOrEmpty()) {
                        //  showToast("wrong quickbloxId")
                    } else {

                        startCall(
                            false,
                            ArrayList(opponents.filter { it.id == quickBloxId })

                        )
                        Log.v("shivaay", "Call recieve")

                    }

                    //  }
                    if (checkPermissions(PERMISSIONS)) {
                        startPermissionsActivity(false)
                    }
                }
            }, listener2 = object : VideoCallInterface {
                override fun onVideocallClick(quickBloxId: Int) {
                    //  if (checkIsLoggedInChat()) {
                    if (opponents.filter { it.id == quickBloxId }.isNullOrEmpty()) {
                        // showToast("wrong quickbloxId")
                    } else {
                        startCall(
                            true,
                            ArrayList(opponents.filter { it.id == quickBloxId })
                        )
                        Log.v("shivaay", "Call done")

                    }
                    // }
                    if (checkPermissions(PERMISSIONS)) {
                        startPermissionsActivity(false)
                    }
                }
            })

        appointmentAdapter.setOnClickInterface(object : CancelAppointmentInterface {
            override fun onCancelClick(userId: Int, coachSlotTimeId: Int, appointmentId: Int) {

                userId1 = userId

                coachSlotTimeId1 = coachSlotTimeId

                appointmentId1 = appointmentId

                cancelAppointment()

            }
        })

        appointmentBinding.upcomingAppointmentRv.adapter = appointmentAdapter
    }

    private fun cancelAppointment() {

        viewModel.cancelAppointment(
            CancelAppointmentBody(
                coachSlotTimeId1, appointmentId1, userId1,
            )
        )

        viewModel.cancelAppointment.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {
                is NetworkResult.Success -> {

                    val response = it.data!! as AppointmentListResponse

                    viewModel.getAppointmentListFilter(dateFilter)
                    Toast.makeText(context, response.message, Toast.LENGTH_LONG).show()
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

    protected fun showErrorSnackbar(
        @StringRes resId: Int,
        e: Exception?,
        clickListener: View.OnClickListener
    ) {
        val rootView = requireActivity().window.decorView.findViewById<View>(android.R.id.content)
        showErrorSnackbar(rootView, resId, e, R.string.dlg_retry, clickListener)
    }

    @SuppressLint("NewApi")
    @RequiresApi(Build.VERSION_CODES.N)
    private fun callBottomSheet() {
        addNewAppointmentBinding = AddAppointmentLayoutBinding.inflate(layoutInflater)

        bottomSheetDialog = BottomSheetDialog(requireContext())

        bottomSheetDialog.setContentView(addNewAppointmentBinding.root)

        appointmentBinding.addAppointmentBtn.setOnClickListener {
            name=0

            addNewAppointmentBinding.dateTv.text=""

            time=0

            (addNewAppointmentBinding.noteEt as TextView).text = ""

            addNewAppointmentBinding!!.dateTv.text =
                CalendarUtils.dateFormatFit(CalendarUtils.selectedDate.toString())
            viewModel.getUserNameAndTimeSlot1(CalendarUtils.selectedDate.toString())


            try {
                addNewAppointment()

            } catch (e: Exception) {

            }

            //addNewAppointment()
            bottomSheetDialog.show()

        }

        addNewAppointmentBinding.crossBtn.setOnClickListener { bottomSheetDialog.hide() }

        addNewAppointmentBinding.dateTv.setOnClickListener {

            val c = Calendar.getInstance()

            val year = c.get(Calendar.YEAR)

            val month = c.get(Calendar.MONTH)

            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->

                    val months = monthOfYear + 1
                    var date =
                        "$year-${if (months < 10) "0$months" else months}-${if (dayOfMonth < 10) "0$dayOfMonth" else dayOfMonth}"

                    date = CalendarUtils.dateFormatFit(date)

                    addNewAppointmentBinding!!.dateTv.text = date

                    /*  try {
                          var rDate = SimpleDateFormat("yyyy-MM-dd")
                          var rDate2 = rDate.parse(date.toString())
                          var fDate = SimpleDateFormat("dd MMM").format(rDate2)
                          addNewAppointmentBinding.date_tv.text = "Date : $fDate"
                      } catch (e: Exception) {

                      }*/

                    if (date != "") {
                        viewModel.getUserNameAndTimeSlot1(CalendarUtils.dateFormatForApi(date))
                        try {
                            addNewAppointment()
                        } catch (e: Exception) {

                        }
                        //addNewAppointment()

                    }

                },
                year,
                month,
                day
            )

            c.add(Calendar.DAY_OF_MONTH, +7)

            val result: Date = c.time

            dpd.apply {

                datePicker.minDate = System.currentTimeMillis()

                datePicker.maxDate = result.time

                show()

                getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK)

                getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK)
            }
        }
    }

    internal fun showProgressDialog(@StringRes messageId: Int) {
        if (progressDialog == null) {
            progressDialog = ProgressDialog(requireContext())
            progressDialog?.isIndeterminate = true
            progressDialog?.setCancelable(false)
            progressDialog?.setCanceledOnTouchOutside(false)
            progressDialog?.setOnKeyListener(KeyEventListener())
        }
        progressDialog?.setMessage(getString(messageId))
        progressDialog?.show()
    }

    internal fun hideProgressDialog() {
        if (progressDialog?.isShowing == true) {
            progressDialog?.dismiss()
        }
    }

    inner class KeyEventListener : DialogInterface.OnKeyListener {
        override fun onKey(dialog: DialogInterface?, keyCode: Int, keyEvent: KeyEvent?): Boolean {
            return keyCode == KeyEvent.KEYCODE_BACK
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun getDetailsList() {

        if (dateFilter == "") {
            dateFilter = CalendarUtils.currentDate().toString()
        }

        viewModel.getAppointmentListFilter(dateFilter)

        viewModel.getAppointmentListFilter.observe(viewLifecycleOwner) {

            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data!! as AppointmentListResponse

                    if (response.CoachAppointment.isNullOrEmpty()) {
                        appointmentBinding.toastA.visibility = View.VISIBLE
                        response.CoachAppointment?.let { it1 -> appointmentAdapter.setList(it1) }
                    } else {
                        appointmentBinding.toastA.visibility = View.GONE
                        response.CoachAppointment?.let { it1 -> appointmentAdapter.setList(it1) }
                    }


                }
                is NetworkResult.Error -> {
                    val response =
                        Util.error(it.data!!, AppointmentListResponse::class.java)
                    viewModel.codeSend.observe(viewLifecycleOwner) {
                        val codeGet = it
                        when (it) {
                            404 -> appointmentBinding.toastA.visibility = View.VISIBLE
                            401 -> Util.toast(requireContext(), "$it")
                            else -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun addNewAppointment() {

        viewModel.getUserNameAndTimeSlot.observe(viewLifecycleOwner) {

            addNewAppointmentBinding.addNameSpinner.apply {

                when (it) {
                    is NetworkResult.Success -> {
                        val body = it.data as UserNameAndTimeSlotResponse
                        nameList1.clear()
                        nameList1.add("Please Select Client Name")
                        for (element in body.user!!) {
                            if (element.clientName != null && element.clientName != "") {
                                nameList1.add(element.clientName.toString())
                            }
                        }
//                        nameList1.addAll(body.user!!)
                        val adapter: ArrayAdapter<*> =
                            ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_list_item_activated_1,
                                nameList1 as List<Any?>
                            )
                        setAdapter(adapter)
                        if (nameU != 0) {
                            run breaking@{
                                body.user.forEachIndexed { index, element ->
                                    if (element.clientId == nameU) {
                                        nameU = 0
                                        name = element.clientId
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        }
                        onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                            ) {

                                (parent?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                                if (position > 0) {
                                    for (element in body.user!!) {
                                        if (parent.selectedItem.toString() == element.clientName ?: "") {
                                            name = element.clientId
                                        }
                                    }
                                } else {
                                    (parent?.getChildAt(0) as TextView).setTextColor(Color.GRAY)
                                }
                                /*  name =
                                      (parent!!.selectedItem as UserNameAndTimeSlotResponse.UserNameData).clientId*/
                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }


                    }
                    is NetworkResult.Error -> {

                        val response = Util.error(it.data, UserNameAndTimeSlotResponse::class.java)

                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {
                    }
                }

            }

            addNewAppointmentBinding.addTimeSpinner.apply {

                when (it) {
                    is NetworkResult.Success -> {
                        val body = it.data as UserNameAndTimeSlotResponse

                        timeList1.clear()
                        timeList1.add("Select Time Slot")

                        for (element in body.data!!) {
                            if (element.slotTime != null && element.slotTime != "") {
                                timeList1.add(CalendarUtils.timeFormatFit(element.slotTime))
                            }
                        }
//                        timeList.addAll(body.data!!)
                        val adapter: ArrayAdapter<*> =
                            ArrayAdapter<Any?>(
                                requireContext(),
                                android.R.layout.simple_list_item_activated_1,
                                timeList1 as List<Any?>
                            )
                        setAdapter(adapter)
                        if (timeU != 0) {
                            run breaking@{
                                body.data.forEachIndexed { index, element ->
                                    if (element.coachTimeSlotId == timeU) {
                                        timeU = 0
                                        time = element.coachTimeSlotId
                                        setSelection(index)
                                        return@breaking
                                    }
                                }
                            }
                        }
                        onItemSelectedListener = object :
                            AdapterView.OnItemSelectedListener {
                            override fun onItemSelected(
                                parent: AdapterView<*>?,
                                view: View,
                                position: Int,
                                id: Long
                            ) {

                                (parent?.getChildAt(0) as TextView).setTextColor(Color.WHITE)
                                if (position > 0) {
                                    for (element in body.data!!) {
                                        if (parent.selectedItem.toString() == CalendarUtils.timeFormatFit(
                                                element.slotTime
                                            )
                                        ) {
                                            time = element.coachTimeSlotId
                                        }
                                    }
                                    /*time =
                                        (parent!!.selectedItem as UserNameAndTimeSlotResponse.SlotList).coachTimeSlotId*/
                                } else {
                                    (parent?.getChildAt(0) as TextView).setTextColor(Color.GRAY)
                                }


                            }

                            override fun onNothingSelected(parent: AdapterView<*>?) {}
                        }

                    }
                    is NetworkResult.Error -> {

                        val response =
                            Util.error(it.data, UserNameAndTimeSlotResponse::class.java)

                        Toast.makeText(requireContext(), response.message, Toast.LENGTH_LONG)
                            .show()
                    }
                    is NetworkResult.Loading -> {
                    }
                }

            }
        }

        addNewAppointmentBinding.apply {

            saveBtn.setOnClickListener {
                val validation = viewModel.validationOnAddAppointment(
                    name,
                    dateTv.text.trim().toString(),
                    time,
                    noteEt.text.trim().toString()
                )

                if (validation.first) {

                    viewModel.addNewAppointment(
                        AddNewAppointmentBody(
                            time,
                            CalendarUtils.dateFormatForApi(dateTv.text.trim().toString()),
                            name,
                            noteEt.text.trim().toString()
                        )
                    )

                    viewModel.getAppointmentListFilter(dateFilter)

                    bottomSheetDialog.hide()

                } else {
                    Util.toast(requireContext(), validation.second)
                }
            }
        }

        viewModel.addNewAppointment.observe(viewLifecycleOwner) {
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data as AddNewAppointmentResponse
                }
                is NetworkResult.Error -> {

                    val response = Util.error(
                        it.data,
                        AddNewAppointmentResponse::class.java
                    )

                    Toast.makeText(
                        requireContext(),
                        response.message,
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
                is NetworkResult.Loading -> {
                    loading.showProgress()
                }
            }
        }

    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onResume() {
        super.onResume()
        val isIncomingCall = SharedPrefsHelper.get(EXTRA_IS_INCOMING_CALL, false)

        dateFilter = CalendarUtils.currentDate().toString()

        viewModel.getAppointmentListFilter(dateFilter)
        appointmentAdapter.notifyDataSetChanged()


        if (isCallServiceRunning(CallService::class.java)) {
            Log.d(TAG, "CallService is running now")

            CallActivity.start(requireContext(), isIncomingCall)

        }
        //  clearAppNotifications()
        loadUsers()
    }


    private fun isCallServiceRunning(serviceClass: Class<*>): Boolean {
        val manager =
            requireActivity().getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        val services = manager.getRunningServices(Integer.MAX_VALUE)
        for (service in services) {
            if (CallService::class.java.name == service.service.className) {
                return true
            }
        }
        return false
    }

    private fun clearAppNotifications() {
        val notificationManager =
            requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.cancelAll()
    }


    private fun startPermissionsActivity(checkOnlyAudio: Boolean) {
        PermissionsActivity.startForResult(requireActivity(), checkOnlyAudio, PERMISSIONS)
    }


    private fun loadUsers() {
        isLoading = true
        // loading = CustomProgressLoading(requireContext())
        loading.showProgress()
        // hideProgressDialog()
        // showProgressDialog(R.string.dlg_loading_opponents)
        currentPage += 1
        val rules = ArrayList<GenericQueryRule>()
        rules.add(GenericQueryRule(ORDER_RULE, ORDER_DESC_UPDATED))
        val requestBuilder = QBPagedRequestBuilder()
        requestBuilder.rules = rules
        requestBuilder.perPage = PER_PAGE_SIZE_100
        requestBuilder.page = currentPage

        QBUsers.getUsers(requestBuilder).performAsync(object : QBEntityCallback<ArrayList<QBUser>> {
            override fun onSuccess(qbUsers: ArrayList<QBUser>?, bundle: Bundle?) {
                qbUsers?.let {
                    Log.d(TAG, "Successfully loaded users")
                    QbUsersDbManager.saveAllUsers(qbUsers, true)

                    val totalPagesFromParams = bundle?.get(TOTAL_PAGES_BUNDLE_PARAM) as Int
                    if (currentPage >= totalPagesFromParams) {
                        hasNextPage = false
                    }

                    if (currentPage == 1) {
                        initOpponents()
                    } else {
                        //usersAdapter?.addUsers(qbUsers)
                    }
                }
                loading.hideProgress()
                isLoading = false
            }

            override fun onError(e: QBResponseException?) {
                e?.let {
                    Log.d(TAG, "Error load users" + e.message)
                    loading.hideProgress()
                    isLoading = false
                    currentPage -= 1
                    showErrorSnackbar(R.string.loading_users_error, e) {
                        loadUsers()
                    }
                }
            }
        })
    }


    private fun initOpponents() {
        opponents = QbUsersDbManager.allUsers
        try {
            opponents.remove(SharedPrefsHelper.getCurrentUser())
        } catch (e: Exception) {

        }

        /*  if (usersAdapter == null) {
              usersAdapter = UsersAdapter(requireActivity(), opponents)
              usersAdapter?.setSelectedItemsCountsChangedListener(object :
                  UsersAdapter.SelectedItemsCountsChangedListener {
                  override fun onCountSelectedItemsChanged(count: Int) {

                  }
              })

              usersRecyclerView.layoutManager = LinearLayoutManager(requireContext())
              usersRecyclerView.adapter = usersAdapter
              usersRecyclerView.addOnScrollListener(ScrollListener(usersRecyclerView.layoutManager as LinearLayoutManager))
          } else {
              usersAdapter?.updateUsersList(opponents)
          }*/
    }


    private inner class ScrollListener(val layoutManager: LinearLayoutManager) :
        RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            if (!isLoading && hasNextPage && dy > 0) {
                val visibleItemCount = layoutManager.childCount
                val totalItemCount = layoutManager.itemCount
                val firstVisibleItem = layoutManager.findFirstVisibleItemPosition()

                val needToLoadMore = visibleItemCount * 2 + firstVisibleItem >= totalItemCount
                if (needToLoadMore) {
                    loadUsers()
                }
            }
        }
    }


    private fun startCall(
        isVideoCall: Boolean, clientdata: ArrayList<QBUser>
    ) {
        // val usersCount = usersAdapter?.selectedUsers?.size
        /* if (usersCount != null && usersCount > MAX_OPPONENTS_COUNT) {
             longToast(String.format(getString(R.string.error_max_opponents_count), MAX_OPPONENTS_COUNT))
             return
         }*/

        val userIds = clientdata.map { it.id }
        //showToast("TAppontment Startcall")


        val conferenceType = if (isVideoCall) {
            QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_VIDEO
        } else {
            QBRTCTypes.QBConferenceType.QB_CONFERENCE_TYPE_AUDIO
        }
        val rtcClient = QBRTCClient.getInstance(requireContext())
        val session = rtcClient.createNewSessionWithOpponents(userIds, conferenceType)
        WebRtcSessionManager.setCurrentSession(session)

        // make Users FullName Strings and id's list for iOS VOIP push
        val sessionId = session.sessionID
        val opponentIds = ArrayList<String>()
        val opponentNames = ArrayList<String>()

        val usersInCall = clientdata


        // the Caller in exactly first position is needed regarding to iOS 13 functionality
        usersInCall.add(0, currentUser)

        for (user in usersInCall) {
            val userId = user.id.toString()
            val name = user.fullName ?: user.login
            opponentIds.add(userId)
            opponentNames.add(name)


        }
        val userInfo = HashMap<String, String>()
        userInfo["name"] = Hawk.get(NAME)
        val img: String? = Hawk.get(IMAGE)
        if (img == "empty")
            ""
        else userInfo["image"] = Hawk.get(IMAGE)

/*
        val userInfo2 = HashMap<String, String>()
        val img:String? = Hawk.get(IMAGE)
        if(img=="empty")
            ""
        else userInfo2["image"] = Hawk.get(IMAGE)*/

        Log.v("hello", userInfo.get("image").toString())
        Log.v("hell", userInfo.get("name").toString())


        val idsInLine = TextUtils.join(",", opponentIds)
        val namesInLine = TextUtils.join(",", opponentNames)



        Log.d(TAG, "New Session with id: $sessionId\n Users in Call: \n$idsInLine\n$namesInLine")

        userIds?.forEach { userId ->
            Executor.addTask(object : ExecutorTask<Boolean> {
                override fun onBackground(): Boolean {
                    val timeout3Seconds = 3000L
                    return QBChatService.getInstance().pingManager.pingUser(userId, timeout3Seconds)
                }

                override fun onForeground(result: Boolean) {
                    if (result) {
                        val message =
                            "Participant with id: $userId is online. There is no need to send a VoIP notification."
                        Log.d(TAG, message)
                    } else {
                        sendPushMessage(
                            userId,
                            currentUser.login,
                            sessionId,
                            idsInLine,
                            namesInLine,
                            isVideoCall
                        )
                    }
                }

                override fun onError(exception: Exception) {
                    shortToast(exception.message.toString())
                }
            })
        }
        session.startCall(userInfo)
        // Log.v("hello",userInfo.get("name").toString())

        CallActivity.start(requireContext(), false)


    }

    private fun unsubscribeFromPushes(callback: () -> Unit) {
        if (QBPushManager.getInstance().isSubscribedToPushes) {
            QBPushManager.getInstance().addListener(SubscribeListener(TAG, callback))
            SubscribeService.unSubscribeFromPushes(context)
        } else {
            callback()
        }
    }

    private fun checkIsLoggedInChat(): Boolean {
        if (!QBChatService.getInstance().isLoggedIn) {

            // shortToast(R.string.login_chat_retry)

            startLoginService()

            return false

        }
        return true
    }

    protected fun checkPermissions(permissions: Array<String>): Boolean {
        for (permission in permissions) {
            if (checkPermission(permission)) {
                return true
            }
        }
        return false
    }

    private fun startLoginService() {
        if (SharedPrefsHelper.hasCurrentUser()) {
            LoginService.loginToChatAndInitRTCClient(
                requireContext(),
                SharedPrefsHelper.getCurrentUser()
            )
        }
    }


    protected fun checkPermission(permission: String): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            permission
        ) == PackageManager.PERMISSION_DENIED
    }


    private inner class SubscribeListener(val tag: String?, val callback: () -> Unit) :
        QBPushManager.QBSubscribeListener {
        override fun onSubscriptionDeleted(success: Boolean) {
            QBPushManager.getInstance().removeListener(this)
            callback()
        }

        override fun onSubscriptionCreated() {
            // empty
        }

        override fun onSubscriptionError(p0: Exception?, p1: Int) {
            // empty
        }

        override fun equals(other: Any?): Boolean {
            if (other is SubscribeListener) {
                return tag == other.tag
            }
            return false
        }

        override fun hashCode(): Int {
            var hash = 1
            hash = 31 * hash + tag.hashCode()
            return hash
        }
    }


}
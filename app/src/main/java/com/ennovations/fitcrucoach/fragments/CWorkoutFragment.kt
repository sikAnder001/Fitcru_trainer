package com.ennovations.fitcrucoach.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.CWorkoutAdapter
import com.ennovations.fitcrucoach.adapter.FilterAdapter
import com.ennovations.fitcrucoach.databinding.FragmentCWorkoutBinding
import com.ennovations.fitcrucoach.model.FilterModel
import com.ennovations.fitcrucoach.response.ClientListResponse
import com.ennovations.fitcrucoach.response.NotificationListResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ClientListViewModel
import com.ennovations.fitcrucoach.view_model.NotificationsViewModel
import com.quickblox.auth.session.QBSettings
import com.quickblox.messages.services.QBPushManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class CWorkoutFragment : Fragment() {

    private lateinit var workoutBinding: FragmentCWorkoutBinding

    private lateinit var workoutAdapter: CWorkoutAdapter

    private lateinit var filterAdapter: FilterAdapter

    private var filter = ""

    private val clientListViewModel by viewModels<ClientListViewModel>()
    private val clientListViewModel1 by viewModels<NotificationsViewModel>()

    private lateinit var loading: CustomProgressLoading

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workoutBinding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_c_workout, container, false)

        loading = CustomProgressLoading(requireContext())

        getClientList()

        hittingViews()

        getNotificationCount()
        workoutBinding.notificationBtn.setOnClickListener {
            view?.findNavController()
                ?.navigate(R.id.action_CWorkoutFragment2_to_notificationList)
        }

        return workoutBinding.root

    }

    private fun getNotificationCount() {
        clientListViewModel1.notificationList()
        clientListViewModel1.response.observe(viewLifecycleOwner) {
            when (it) {
                is NetworkResult.Success -> {
                    val data = it.data as NotificationListResponse
                    var count = 0
                    for (element in data.data) {
                        if (element.coach_is_read == "0") {
                            count++
                            continue
                        }
                    }
                    if (count == 0) {
                        workoutBinding.notificationCounter.visibility = View.GONE
                    } else {
                        workoutBinding.notificationCounter.visibility = View.VISIBLE
                        workoutBinding.notificationCounter.text = count.toString()
                    }


                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data, NotificationListResponse::class.java)
//                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    clientListViewModel1.codeSend.observe(viewLifecycleOwner) {
                        val codeGet = it
                        when (it) {
                            404 -> {
                                workoutBinding.notificationCounter.visibility = View.GONE
                                workoutBinding.notificationCounter.text = "0"
                            }
                            else -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                .show()
                        }
                    }
                    /*certificateViewModel.codeSend.observe(viewLifecycleOwner) {
                            val codeGet = it
                            when (it) {
                                404 -> certificateBinding.toastTv.visibility = View.VISIBLE
                                else -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }*/

                }
                is NetworkResult.Loading -> {
                    //show progress
                    /*loading.showProgress()*/
                }
            }

        }
    }

    private fun getClientList() {
        val linearLayout = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        workoutBinding.clientWorkoutRv.layoutManager = linearLayout

        workoutBinding.clientWorkoutRv.setHasFixedSize(true)

        workoutAdapter = CWorkoutAdapter(
            context,
            object : CWorkoutAdapter.ClientWorkoutOnClickListener {
                override fun onClick(user_id: Int) {
                    val bundle = Bundle()
                    bundle.putInt("user_id", user_id)
                    view?.findNavController()
                        ?.navigate(R.id.action_CWorkoutFragment2_to_mainPlanFragment, bundle)
                }
            })

        workoutBinding.clientWorkoutRv.adapter = workoutAdapter

        loading.showProgress()

        clientListViewModel.getClientList()

        clientListViewModel.clientList.observe(viewLifecycleOwner) {

            loading.hideProgress()
            try {
                when (it) {
                    is NetworkResult.Success -> {
                        val response = it.data as ClientListResponse

                        workoutBinding.totalClientsTv.text =
                            "Total Clients ${response.Clients.size}"
                        if (response.Clients.isNullOrEmpty()) {
                            workoutBinding.toastC.visibility = VISIBLE
                            workoutBinding.clientWorkoutRv.visibility = GONE
                        } else {
                            workoutBinding.toastC.visibility = GONE
                            workoutBinding.clientWorkoutRv.visibility = VISIBLE
                            workoutAdapter.setList(response.Clients)
                        }

                    }

                    is NetworkResult.Error -> {
                        val response = Util.error(it.data, ClientListResponse::class.java)
                        clientListViewModel.codeSend.observe(viewLifecycleOwner) {
                            val codeGet = it
                            when (it) {
                                401 -> {
                                    SessionManager.destroySession()
                                    view?.findNavController()
                                        ?.navigate(R.id.action_CWorkoutFragment2_to_loginFragment3)
                                }
                                404 -> workoutBinding.toastC.visibility = VISIBLE
                                else -> Toast.makeText(context, response.message, Toast.LENGTH_LONG)
                                    .show()
                            }
                        }

                    }
                    is NetworkResult.Loading -> {
                        loading.showProgress()
                    }
                }
            } catch (e: Exception) {
            }
        }


    }

    private fun addDataToAdapter(): ArrayList<FilterModel> {
        val filter: ArrayList<FilterModel> = ArrayList()
        filter.apply {
            add(
                FilterModel(
                    "First Join date "
                )
            )

            add(
                FilterModel(
                    "First expiration date "
                )
            )

            add(
                FilterModel(
                    "Last message received"
                )
            )

            add(
                FilterModel(
                    "More uncompleted goals"
                )
            )
        }
        return filter
    }

    private fun hittingViews() {

        workoutBinding.apply {

            var name = SessionManager.getCoachDetails().coach_name
            name = name[0].toString().uppercase()

            backBtn.text = name

            backBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_CWorkoutFragment2_to_myProfileFragment)
            }

            filterBtn.setOnClickListener {

                val view = View.inflate(requireContext(), R.layout.workout_filter, null)

                val builder = AlertDialog.Builder(requireContext(), R.style.AlertDialogStyle)
                builder.setView(view)

                val dialog = builder.create()
                dialog.apply {
                    setContentView(R.layout.workout_filter)
                    window?.apply {
                        setGravity(Gravity.TOP)
                        setBackgroundDrawableResource(android.R.color.transparent)
                    }
                }

                filterAdapter =
                    FilterAdapter(requireContext(), object : FilterAdapter.FilterOnClickListener {
                        override fun onClick(position: Int) {
                            dialog.dismiss()
                            when (position) {
                                0 -> filter = "fsd"
                                1 -> filter = "fed"
                                2 -> filter = "lmr"
                                3 -> filter = "mug"
                            }
                            clientListViewModel.getClientFilter(filter)

                            clientListViewModel.getClientFilter.observe(viewLifecycleOwner) {

                                loading.hideProgress()
                                when (it) {
                                    is NetworkResult.Success -> {

                                        val response = it.data as ClientListResponse

                                        workoutBinding.totalClientsTv.text =
                                            "Total Clients ${response.Clients.size}"

                                        if (response.Clients.isNullOrEmpty()) {
                                            workoutBinding.toastC.visibility = VISIBLE
                                            workoutBinding.clientWorkoutRv.visibility = GONE
                                        } else {
                                            workoutBinding.toastC.visibility = GONE
                                            workoutBinding.clientWorkoutRv.visibility = VISIBLE
                                            workoutAdapter.setList(response.Clients)
                                        }

                                    }
                                    is NetworkResult.Error -> {
                                        val response =
                                            Util.error(it.data, ClientListResponse::class.java)
                                        clientListViewModel.codeSend.observe(viewLifecycleOwner) {
                                            val codeGet = it
                                            when (it) {
                                                401 -> {
                                                    workoutBinding.totalClientsTv.text =
                                                        "Total Clients 0"
                                                }
                                                else -> Toast.makeText(
                                                    context,
                                                    response.message,
                                                    Toast.LENGTH_LONG
                                                )
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
                    })

                val filterRecyclerView: RecyclerView = view.findViewById(R.id.filter_rv)

                filterRecyclerView.apply {
                    layoutManager =
                        LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    adapter = filterAdapter
                }
                filterAdapter.addData(addDataToAdapter())
                dialog.show()

            }

        }

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        QBSettings.getInstance().isEnablePushNotification = true




        QBPushManager.getInstance().addListener(object : QBPushManager.QBSubscribeListener {
            override fun onSubscriptionCreated() {
                Log.v("unsubscribe", "onSubscriptionCreated")
            }

            override fun onSubscriptionError(p0: java.lang.Exception?, p1: Int) {
                Log.v("subscribe", "onSubscriptionError")
            }

            override fun onSubscriptionDeleted(p0: Boolean) {
                Log.v("unsubscribe", "onSubscriptionDeleted")
            }


        })

    }

}
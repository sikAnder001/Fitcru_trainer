package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.Network.TokenManager
import com.ennovations.fitcrucoach.adapter.NotificationListAdapter
import com.ennovations.fitcrucoach.databinding.FragmentNotificationListBinding
import com.ennovations.fitcrucoach.response.NotificationListResponse
import com.ennovations.fitcrucoach.response.ReadNotificationResponse
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.NotificationsViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class NotificationList : Fragment() {
    private lateinit var binding: FragmentNotificationListBinding
    lateinit var notificationListAdapter: NotificationListAdapter

    private val viewModel by viewModels<NotificationsViewModel>()

    private lateinit var loading: CustomProgressLoading

    @Inject
    lateinit var tokenManager: TokenManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentNotificationListBinding.inflate(inflater, container, false)

        loading = CustomProgressLoading(requireContext())

        binding.gobackbtn.setOnClickListener {
            requireActivity().onBackPressed()
        }
        getNotificationList()

        return binding.root
    }

    private fun getNotificationList() {
        notificationListAdapter =
            NotificationListAdapter(context, object : NotificationListAdapter.ReadNotificationItem {
                override fun onClick(id: Int) {
                    readNotification(id)
                }
            })

        binding.notificationsRv.adapter = notificationListAdapter

        viewModel.notificationList()
        viewModel.response.observe(viewLifecycleOwner) {
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {
                    val response = it.data as NotificationListResponse
                    notificationListAdapter.setList(response!!.data)
//                    notificationListAdapter.notifyDataSetChanged()

                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data, NotificationListResponse::class.java)
//                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()
                    viewModel.codeSend.observe(viewLifecycleOwner) {
                        val codeGet = it
                        when (it) {
                            404 -> binding.toastTv.visibility = View.VISIBLE
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

    private fun readNotification(id: Int) {
        viewModel.readNotification(id)
        viewModel.readResponse.observe(viewLifecycleOwner) {
            loading.hideProgress()
            when (it) {
                is NetworkResult.Success -> {

                }
                is NetworkResult.Error -> {
                    val response = Util.error(it.data, ReadNotificationResponse::class.java)
                    Toast.makeText(context, response.message, Toast.LENGTH_SHORT).show()

                }
                is NetworkResult.Loading -> {
                    //show progress
                    /*loading.showProgress()*/
                }

            }

        }
    }

}
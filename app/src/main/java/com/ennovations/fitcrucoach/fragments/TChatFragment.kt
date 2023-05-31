package com.ennovations.fitcrucoach.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import com.ennovations.fitcrucoach.Network.CustomProgressLoading
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.R
import com.ennovations.fitcrucoach.adapter.ChatAdapter
import com.ennovations.fitcrucoach.databinding.FragmentTChatBinding
import com.ennovations.fitcrucoach.response.ChatListResponse
import com.ennovations.fitcrucoach.response.ForgotPasswordResponse
import com.ennovations.fitcrucoach.utility.SessionManager
import com.ennovations.fitcrucoach.utility.Util
import com.ennovations.fitcrucoach.view_model.ChatViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TChatFragment : Fragment(), ChatAdapter.ChatOnClickListener {

    private lateinit var chatBinding: FragmentTChatBinding

    private lateinit var chatAdapter: ChatAdapter

    private val viewModel by viewModels<ChatViewModel>()

    private lateinit var loading: CustomProgressLoading

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        chatBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_t_chat, container, false)

        loading = CustomProgressLoading(requireContext())

        getChatList()

        hittingViews()

        return chatBinding.root
    }

    private fun hittingViews() {
        chatBinding.apply {
            var name = SessionManager.getCoachDetails().coach_name
            name = name[0].toString().uppercase()

            backBtn.text = name

            backBtn.setOnClickListener {
                view?.findNavController()
                    ?.navigate(R.id.action_TChatFragment_to_myProfileFragment)
            }
        }
    }

    private fun getChatList() {
        chatAdapter = ChatAdapter(activity, this)

        chatBinding.chatRv.adapter = chatAdapter

        viewModel.getChatList()

        viewModel.chatList.observe(viewLifecycleOwner) {

            loading.hideProgress()

            when (it) {

                is NetworkResult.Success -> {

                    val chatListResponse = it.data as ChatListResponse

                    chatAdapter.setList(chatListResponse.data)
                }

                is NetworkResult.Error -> {

                    val response = Util.error(it.data, ForgotPasswordResponse::class.java)

                    viewModel.codeSend.observe(viewLifecycleOwner) {
                        val codeGet = it
                        when (it) {
                            404 -> chatBinding.toastTv.visibility = View.VISIBLE
                            else -> Toast.makeText(
                                requireContext(),
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

    override fun onResume() {
        loading.showProgress()
        viewModel.getChatList()
        super.onResume()
    }

    override fun onClick(position: Int) {

    }


}
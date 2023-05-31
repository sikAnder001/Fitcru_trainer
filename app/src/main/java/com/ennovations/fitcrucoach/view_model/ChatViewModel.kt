package com.ennovations.fitcrucoach.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.LastMessage
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(val repository: Repository) : ViewModel() {

    val codeSend = MutableLiveData<Int?>()

    var chatList = MutableLiveData<NetworkResult<Any>>()
    var lastMessageS = MutableLiveData<NetworkResult<Any>>()

    fun getChatList() {
        viewModelScope.launch {
            chatList.value = NetworkResult.Loading()
            val t = Util.handleResponse(chatList, repository.getChatList())
            codeSend.value = t.code()
        }
    }

    fun lastMessage(lastMessage: LastMessage) {
        viewModelScope.launch {
            lastMessageS.value = NetworkResult.Loading()
            Util.handleResponse(chatList, repository.lastMessage(lastMessage))
        }
    }
}
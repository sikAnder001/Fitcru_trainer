package com.ennovations.fitcrucoach.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {
    val codeSend = MutableLiveData<Int?>()
    val response = MutableLiveData<NetworkResult<Any>>()
    val readResponse = MutableLiveData<NetworkResult<Any>>()


    fun notificationList() {
        viewModelScope.launch {
            response.value = NetworkResult.Loading()
            val t = Util.handleResponse(response, repository.notificationList())
            codeSend.value = t.code()
        }
    }

    fun readNotification(id: Int) {
        viewModelScope.launch {
            readResponse.value = NetworkResult.Loading()
            Util.handleResponse(readResponse, repository.readNotification(id))
        }
    }

}
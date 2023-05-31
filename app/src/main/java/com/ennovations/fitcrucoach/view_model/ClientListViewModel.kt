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
class ClientListViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val codeSend = MutableLiveData<Int?>()

    val clientList = MutableLiveData<NetworkResult<Any>>()
    val clientDetail = MutableLiveData<NetworkResult<Any>>()
    val getClientFilter = MutableLiveData<NetworkResult<Any>>()
    val mealDetail = MutableLiveData<NetworkResult<Any>>()

    fun getClientList() {
        viewModelScope.launch {
            clientList.value = NetworkResult.Loading()
            val t = Util.handleResponse(clientList, repository.getClientList())
            codeSend.value = t.code()
        }
    }

    fun getClientDetails(clientId: Int, selectedDate: String) {
        viewModelScope.launch {
            clientDetail.value = NetworkResult.Loading()
            Util.handleResponse(clientDetail, repository.getClientDetails(clientId, selectedDate))
        }
    }

    fun getClientFilter(filter: String) {
        viewModelScope.launch {
            getClientFilter.value = NetworkResult.Loading()
            var t = Util.handleResponse(getClientFilter, repository.getClientFilter(filter))
            codeSend.value = t.code()
        }
    }

    fun getMealDetail(id: Int, userId: Int, date: String) {
        viewModelScope.launch {
            mealDetail.value = NetworkResult.Loading()
            Util.handleResponse(mealDetail, repository.getMealDetail(id, userId, date))
        }
    }
}

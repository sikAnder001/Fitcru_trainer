package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.AddNewAppointmentBody
import com.ennovations.fitcrucoach.body.CancelAppointmentBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AppointmentViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val codeSend = MutableLiveData<Int?>()

    val cancelAppointment = MutableLiveData<NetworkResult<Any>>()

    val getUserNameAndTimeSlot = MutableLiveData<NetworkResult<Any>>()

    val addNewAppointment = MutableLiveData<NetworkResult<Any>>()

    val markUnavailable = MutableLiveData<NetworkResult<Any>>()

    val getAppointmentListFilter = MutableLiveData<NetworkResult<Any>>()

    val getMarkUnavailability = MutableLiveData<NetworkResult<Any>>()

    val removeUnavailabilityDate = MutableLiveData<NetworkResult<Any>>()


    fun cancelAppointment(body: CancelAppointmentBody) {
        viewModelScope.launch {
            cancelAppointment.value = NetworkResult.Loading()
            Util.handleResponse(cancelAppointment, repository.cancelAppointment(body))
        }
    }

    fun getUserNameAndTimeSlot1(date: String) {
        viewModelScope.launch {
            getUserNameAndTimeSlot.value = NetworkResult.Loading()
            Util.handleResponse(getUserNameAndTimeSlot, repository.getUserNameAndTimeSlot1(date))
        }
    }

    fun getUserNameAndTimeSlot() {
        viewModelScope.launch {
            getUserNameAndTimeSlot.value = NetworkResult.Loading()
            Util.handleResponse(getUserNameAndTimeSlot, repository.getUserNameAndTimeSlot())
        }
    }

    fun addNewAppointment(body: AddNewAppointmentBody) {
        viewModelScope.launch {
            addNewAppointment.value = NetworkResult.Loading()
            Util.handleResponse(addNewAppointment, repository.addNewAppointment(body))
        }
    }

    fun markUnavailable(date: String) {
        viewModelScope.launch {
            markUnavailable.value = NetworkResult.Loading()
            Util.handleResponse(markUnavailable, repository.markUnavailable(date))
        }
    }

    fun getAppointmentListFilter(date: String) {
        viewModelScope.launch {
            getAppointmentListFilter.value = NetworkResult.Loading()
            val t = Util.handleResponse(
                getAppointmentListFilter,
                repository.getAppointmentListFilter(date)
            )
            codeSend.value = t.code()
        }
    }

    fun getMarkedUnavailability() {
        viewModelScope.launch {
            getMarkUnavailability.value = NetworkResult.Loading()
            Util.handleResponse(getMarkUnavailability, repository.getMarkedUnavailable())
        }
    }

    fun removeUnavailabilityDate(selectedData: String) {
        viewModelScope.launch {
            removeUnavailabilityDate.value = NetworkResult.Loading()
            Util.handleResponse(
                removeUnavailabilityDate,
                repository.removeUnavailabilityDate(selectedData)
            )
        }
    }

    fun validationOnAddAppointment(
        name: Int,
        date: String,
        time: Int,
        note: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (name == 0)
            result = Pair(false, "Please select the name")
        else if (TextUtils.isEmpty(date))
            result = Pair(false, "Please select the date")
        else if (time == 0)
            result = Pair(false, "Please select the time slot")
        return result

    }


}
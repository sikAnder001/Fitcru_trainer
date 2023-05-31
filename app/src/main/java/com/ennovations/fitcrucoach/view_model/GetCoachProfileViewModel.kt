package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import javax.inject.Inject

@HiltViewModel
class GetCoachProfileViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val getCoachDetailsResponse = MutableLiveData<NetworkResult<Any>>()

    val updateCoachDetailsResponse = MutableLiveData<NetworkResult<Any>>()

    val logout = MutableLiveData<NetworkResult<Any>>()

    val deactivateAccount = MutableLiveData<NetworkResult<Any>>()

    fun getCoachDetails() {
        viewModelScope.launch {
            getCoachDetailsResponse.value = NetworkResult.Loading()
            Util.handleResponse(getCoachDetailsResponse, repository.getCoachDetails())
        }
    }

    fun updateCoachDetails(body: MultipartBody) {
        viewModelScope.launch {
            updateCoachDetailsResponse.value = NetworkResult.Loading()
            Util.handleResponse(updateCoachDetailsResponse, repository.updateCoachDetails(body))
        }
    }

    fun validateOnDetails(
        coachName: String,
        email: String,
        number: String,
        dob: String,
        bioData: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")

        if (TextUtils.isEmpty(coachName)) {
            result = Pair(false, "Name field cannot be empty")
        } else if (TextUtils.isEmpty(number))
            result = Pair(false, "Mobile field cannot be empty")
        else if (TextUtils.isEmpty(email))
            result = Pair(false, "Email field cannot be empty")
        else if (TextUtils.isEmpty(dob))
            result = Pair(false, "DOB field cannot be empty")
        else if (TextUtils.isEmpty(bioData))
            result = Pair(false, "Bio-Data field cannot be empty")
        else if (number.length < 10)
            result = Pair(false, "Mobile number must be 10 digits")
        return result
    }

    fun logout() {
        viewModelScope.launch {
            logout.value = NetworkResult.Loading()
            Util.handleResponse(logout, repository.logout())
        }
    }

    fun deactivateAccount() {
        viewModelScope.launch {
            deactivateAccount.value = NetworkResult.Loading()
            Util.handleResponse(deactivateAccount, repository.deactivateAccount())
        }
    }

}
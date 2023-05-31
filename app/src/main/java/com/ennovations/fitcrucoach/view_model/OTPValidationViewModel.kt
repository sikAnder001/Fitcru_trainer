package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.OTPVerificationBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OTPValidationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val otpVerification = MutableLiveData<NetworkResult<Any>>()

    fun otpVerification(body: OTPVerificationBody) {
        viewModelScope.launch {
            otpVerification.value = NetworkResult.Loading()
            Util.handleResponse(otpVerification, repository.otpVerification(body))
        }
    }

    fun otpValidation(
        otp: String,
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(otp)
        ) result = Pair(false, "OTP cannot be empty")
        else if (otp.length < 6)
            result = Pair(false, "OTP length must be 6")
        return result
    }

}
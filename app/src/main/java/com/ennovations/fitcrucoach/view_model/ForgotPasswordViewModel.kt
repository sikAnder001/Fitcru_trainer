package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.ForgotPasswordBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Helper
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val forgotResponse = MutableLiveData<NetworkResult<Any>>()

    fun forgotPassword(body: ForgotPasswordBody) {
        viewModelScope.launch {
            forgotResponse.value = NetworkResult.Loading()
            Util.handleResponse(forgotResponse, repository.forgotPassword(body))
        }
    }

    fun forgotPasswordValidation(
        email: String,
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(email)
        ) result = Pair(false, "Email cannot be empty")
        else if (!Helper.isValidEmail(email))
            result = Pair(false, "Email is invalid")
        return result
    }

}
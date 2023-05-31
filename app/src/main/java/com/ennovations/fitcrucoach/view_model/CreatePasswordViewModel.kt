package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.CreatePasswordBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CreatePasswordViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val response = MutableLiveData<NetworkResult<Any>>()

    fun createPassword(body: CreatePasswordBody) {
        viewModelScope.launch {
            response.value = NetworkResult.Loading()
            Util.handleResponse(response, repository.createPassword(body))
        }
    }

    fun passCodeValidation(
        password: String,
        confirmPassword: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(password)
        ) result = Pair(false, "Password cannot be empty")
        else if (TextUtils.isEmpty(confirmPassword)
        ) result = Pair(false, "Confirm password cannot be empty")
        else if (password.length < 8)
            result = Pair(false, "Password length should be equal or greater than 8 ")
        else if (confirmPassword.length < 8)
            result = Pair(
                false, "Confirm password length should be equal or greater than 8"
            )
        else if (password != confirmPassword)
            result = Pair(false, "Password & confirm password  doesn't match")
        return result
    }

}
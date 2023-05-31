package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.ChangePasswordBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChangePasswordViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val changePasswordResponse = MutableLiveData<NetworkResult<Any>>()

    fun changePassword(body: ChangePasswordBody) {
        viewModelScope.launch {
            changePasswordResponse.value = NetworkResult.Loading()
            Util.handleResponse(changePasswordResponse, repository.changePassword(body))
        }
    }

    fun validatePassword(
        oldPassword: String,
        password: String,
        newPassword: String,
    ): Pair<Boolean, String> {
        var result = Pair(true, "")

        if (TextUtils.isEmpty(oldPassword))
            result = Pair(false, "Please enter old password")
        else if (TextUtils.isEmpty(password))
            result = Pair(false, "Please enter new password")
        else if (TextUtils.isEmpty(newPassword)
        ) result = Pair(false, "Please enter confirm password")
        else if (!TextUtils.isEmpty(oldPassword) && oldPassword.length < 8 || !TextUtils.isEmpty(
                password
            ) && password.length < 8
        )
            result = Pair(false, "Password length should be equal or greater than 8 digits")
        else if (!TextUtils.isEmpty(newPassword) && newPassword.length < 8)
            result = Pair(false, "Confirm password should be equal or greater than 8 digits")
        else if (password != newPassword)
            result = Pair(false, "Password does't match")
        else if (newPassword == oldPassword)
            result = Pair(false, "Old password and new password cannot be same")

        return result
    }

}
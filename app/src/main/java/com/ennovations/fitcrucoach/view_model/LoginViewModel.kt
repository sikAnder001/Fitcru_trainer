package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.LoginBody
import com.ennovations.fitcrucoach.body.StoreQuickbloxBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Helper.isValidEmail
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val loginResponse = MutableLiveData<NetworkResult<Any>>()
    val storeQuickbloxResponse = MutableLiveData<NetworkResult<Any>>()
    val fcmToken = MutableLiveData<NetworkResult<Any>>()

    fun loginUser(body: LoginBody) {
        viewModelScope.launch {
            loginResponse.value = NetworkResult.Loading()
            Util.handleResponse(loginResponse, repository.login(body))
        }
    }

    fun saveFCMToken(deviceToken: String) {
        viewModelScope.launch {
            fcmToken.value = NetworkResult.Loading()
            Util.handleResponse(fcmToken, repository.saveFCMToken(deviceToken))
        }
    }

    fun validateCredentials(
        emailAddress: String,
        password: String,
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(emailAddress) /*|| (!isLogin *//*&& TextUtils.isEmpty(userName)*//*)*/
            || TextUtils.isEmpty(password)
        ) result = Pair(false, "Please provide the credentials")
        else if (!isValidEmail(emailAddress))
            result = Pair(false, "Email is invalid")
        else if (!TextUtils.isEmpty(password) && password.length <= 5)
            result = Pair(false, "Password length should be greater than 5")
        return result
    }


    fun storequickbloxid(body: StoreQuickbloxBody) {
        viewModelScope.launch {
            storeQuickbloxResponse.value = NetworkResult.Loading()
            Util.handleResponse(storeQuickbloxResponse, repository.storequickbloxid(body))
        }
    }

}
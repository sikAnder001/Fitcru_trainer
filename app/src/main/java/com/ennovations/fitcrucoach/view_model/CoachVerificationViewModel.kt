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
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class CoachVerificationViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val verificationUpdateResponse = MutableLiveData<NetworkResult<Any>>()

    val getVerificationDetails = MutableLiveData<NetworkResult<Any>>()

    fun updateCoachVerification(
        body: MultipartBody.Part?,
        image2: MultipartBody.Part?,
        requestBodyCN: RequestBody,
        requestBodyLink: RequestBody,
        /* requestBodySlots: RequestBody*/
    ) {

        viewModelScope.launch {
            verificationUpdateResponse.value = NetworkResult.Loading()
            Util.handleResponse(
                verificationUpdateResponse,
                repository.updateCoachVerification(
                    body,
                    image2,
                    requestBodyCN,
                    requestBodyLink
                    /* requestBodySlots*/
                )
            )
        }
    }

    fun getVerificationDetails() {
        viewModelScope.launch {
            getVerificationDetails.value = NetworkResult.Loading()
            Util.handleResponse(
                getVerificationDetails, repository.getVerificationDetails()
            )
        }
    }

    fun validate(
        cardNumber: String,
        link: String,
        /*  slots: String,*/
    ): Pair<Boolean, String> {
        var result = Pair(true, "")

        if (TextUtils.isEmpty(cardNumber)
        ) result = Pair(false, "Please fill Aadhaar card number")
        else if (TextUtils.isEmpty(link))
            result = Pair(false, "Please enter the Instagram link ")
        /*else if (TextUtils.isEmpty(slots))
            result = Pair(false, "slots cannot be empty")*/
        return result
    }

}
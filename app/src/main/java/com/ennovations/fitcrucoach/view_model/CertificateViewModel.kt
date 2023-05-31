package com.ennovations.fitcrucoach.view_model

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
class CertificateViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val codeSend = MutableLiveData<Int?>()

    val certificateResponse = MutableLiveData<NetworkResult<Any>>()

    fun addCertificateDetail(body: MultipartBody) {
        viewModelScope.launch {
            certificateResponse.value = NetworkResult.Loading()
            Util.handleResponse(certificateResponse, repository.addCertificate(body))
        }
    }

    fun getCertificateDetail(id: Int) {
        viewModelScope.launch {
            certificateResponse.value = NetworkResult.Loading()
            val t = Util.handleResponse(certificateResponse, repository.getCertificateDetail(id))
            codeSend.value = t.code()
        }
    }

    fun updateCertificateDetail(body: MultipartBody) {
        viewModelScope.launch {
            certificateResponse.value = NetworkResult.Loading()
            Util.handleResponse(certificateResponse, repository.updateCertificate(body))
        }
    }

}

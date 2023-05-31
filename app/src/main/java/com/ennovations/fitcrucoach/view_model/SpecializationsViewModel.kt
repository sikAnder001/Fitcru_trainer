package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.SpecializationUpdateBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SpecializationsViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val languagesResponse = MutableLiveData<NetworkResult<Any>>()

    val specialization = MutableLiveData<NetworkResult<Any>>()

    val association = MutableLiveData<NetworkResult<Any>>()

    val updateSpecializationDetails = MutableLiveData<NetworkResult<Any>>()

    val getSpecializationDetails = MutableLiveData<NetworkResult<Any>>()

    fun getLanguages() {
        viewModelScope.launch {
            languagesResponse.value = NetworkResult.Loading()
            Util.handleResponse(languagesResponse, repository.getLanguages())
        }
    }

    fun getSpecialization() {
        viewModelScope.launch {
            specialization.value = NetworkResult.Loading()
            Util.handleResponse(specialization, repository.getSpecialization())
        }
    }

    fun getAssociation() {
        viewModelScope.launch {
            association.value = NetworkResult.Loading()
            Util.handleResponse(association, repository.getAssociation())
        }
    }

    fun updateSpecializationDetails(body: SpecializationUpdateBody) {
        viewModelScope.launch {
            updateSpecializationDetails.value = NetworkResult.Loading()
            Util.handleResponse(
                updateSpecializationDetails,
                repository.updateSpecializationDetails(body)
            )
        }
    }

    fun getSpecializationDetails() {
        viewModelScope.launch {
            getSpecializationDetails.value = NetworkResult.Loading()
            Util.handleResponse(
                getSpecializationDetails, repository.getSpecializationDetails()
            )
        }
    }

    fun validationOnQualification(
        lName: String,
        sName: String,
        aName: String,
        qualification: String,
    ): Pair<Boolean, String> {
        var result = Pair(true, "")

        if (lName.isEmpty()) {
            result = Pair(false, "Please select language!")
        } else if (sName.isEmpty()) {
            result = Pair(false, "Please select specialization!")
        } else if (aName.isEmpty()) {
            result = Pair(false, "Please select association!")
        } else if (TextUtils.isEmpty(qualification))
            result = Pair(false, "Qualification cannot be empty")
        return result
    }

}
package com.ennovations.fitcrucoach.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.LocationUpdateBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LocationViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val countriesResponse = MutableLiveData<NetworkResult<Any>>()

    val stateResponse = MutableLiveData<NetworkResult<Any>>()

    val cityResponse = MutableLiveData<NetworkResult<Any>>()

    val updateLocationResponse = MutableLiveData<NetworkResult<Any>>()

    val getLocationDetails = MutableLiveData<NetworkResult<Any>>()

    fun getCountries() {
        viewModelScope.launch {
            countriesResponse.value = NetworkResult.Loading()
            Util.handleResponse(countriesResponse, repository.getCountries())
        }
    }

    fun getState(country: Int) {
        viewModelScope.launch {
            stateResponse.value = NetworkResult.Loading()
            Util.handleResponse(stateResponse, repository.getStates(country))
        }
    }

    fun getCity(state: Int) {
        viewModelScope.launch {
            cityResponse.value = NetworkResult.Loading()
            Util.handleResponse(cityResponse, repository.getCity(state))
        }
    }

    fun getLocationDetails() {
        viewModelScope.launch {
            getLocationDetails.value = NetworkResult.Loading()
            Util.handleResponse(getLocationDetails, repository.getLocationDetails())
        }
    }

    fun updateLocation(body: LocationUpdateBody) {
        viewModelScope.launch {
            updateLocationResponse.value = NetworkResult.Loading()
            Util.handleResponse(updateLocationResponse, repository.updateLocation(body))
        }
    }

    fun validationOnPinCode(
        pinCode: String,
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (pinCode.isEmpty())


            result = Pair(false, "Pincode cannot be empty")
        else if (pinCode.length < 6 || pinCode.length > 6)
            result = Pair(false, "Pincode accept minimum and maximum 6 digits")
        /* else if(TextUtils.isEmpty(pinCode)<6.toString()){
             result = Pair(false, "Pincode accept minimum and maximum 6 digits")
         }*/

        return result
    }

}
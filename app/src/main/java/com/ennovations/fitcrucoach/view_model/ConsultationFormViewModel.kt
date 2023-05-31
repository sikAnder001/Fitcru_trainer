package com.ennovations.fitcrucoach.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.ConsultationFormBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ConsultationFormViewModel @Inject constructor(private val repository: Repository) :
    ViewModel() {

    val sendCode = MutableLiveData<Int>()

    val response = MutableLiveData<NetworkResult<Any>>()

    val response1 = MutableLiveData<NetworkResult<Any>>()

    fun consultationFormDetail(body: ConsultationFormBody) {
        viewModelScope.launch {
            response1.value = NetworkResult.Loading()
            Util.handleResponse(response1, repository.consultationFormDetail(body))
        }
    }

    fun getConsultation(id: Int) {
        viewModelScope.launch {
            response.value = NetworkResult.Loading()
            val t = Util.handleResponse(response, repository.getConsultation(id))
            sendCode.value = t.code()
        }
    }

    fun validationConsultation(
        countryCity: String,
        MarriedSingle: String,
        dailyRoutine: String,
        editOrNot: String,
        medications: String,
        //notes: String,
        stageOfClient: String,
        calories: String,
        /* carbs: Int,
         proteins: Int,
         fats: Int,*/
        workoutProgram: String,
        shortGoalValue: String,
        //targetValue: Int,
        targetDate: String,
        longGoalValue: String,
        //targetValue1: Int,
        targetDate1: String,
        startDate: String,
        /*water: Int,
        steps: Int,*/
        overAllCal: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (countryCity.isNullOrEmpty())
            result = Pair(false, "Please fill country & city")
        else if (MarriedSingle == "Please select a value")
            result = Pair(false, "Please select the status")
        else if (dailyRoutine.isNullOrEmpty())
            result = Pair(false, "Please fill your daily routine")
        else if (editOrNot == "Please select a value")
            result = Pair(false, "Please select  do you want me to edit meal?")
        else if (medications == "Please select a value")
            result = Pair(false, "Please select  do you do you take any medications?")
        /* else if (notes.isNullOrEmpty())
             result = Pair(false, "Please fill note")*/
        else if (stageOfClient.isNullOrEmpty())
            result = Pair(false, "Please fill stage of client")
        else if (calories == "")
            result = Pair(false, "Please fill calories")
        /*else if (carbs == 0)
            result = Pair(false, "Please fill carbs")
        else if (proteins == 0)
            result = Pair(false, "Please fill proteins")
        else if (fats == 0)
            result = Pair(false, "Please fill fat")*/
        else if (workoutProgram.isNullOrEmpty())
            result = Pair(false, "Please fill workout program")
        else if (shortGoalValue == "Please select a value")
            result = Pair(false, "Please select short goal value")
        /*else if (targetValue == 0)
            result = Pair(false, "Please fill short goal target value")*/
        else if (targetDate.isNullOrEmpty())
            result = Pair(false, "Please fill short goal target date")
        else if (longGoalValue == "Please select a value")
            result = Pair(false, "Please select long goal value")
        /*else if (targetValue1 == 0)
            result = Pair(false, "Please fill long goal target value")*/
        else if (targetDate1.isNullOrEmpty())
            result = Pair(false, "Please fill long goal target date")
        else if (startDate.isNullOrEmpty())
            result = Pair(false, "Please fill start date")
        /*else if (water == 0)
            result = Pair(false, "Please fill water value")
        else if (steps == 0)
            result = Pair(false, "Please fill steps")*/
        else if (overAllCal == "")
            result = Pair(false, "Please fill overall calories")
        return result

    }

}

package com.ennovations.fitcrucoach.view_model

import android.text.TextUtils
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.body.EditGoalsBody
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditGoalsViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val sendCode = MutableLiveData<Int>()

    val editGoals = MutableLiveData<NetworkResult<Any>>()

    val getEditGoals = MutableLiveData<NetworkResult<Any>>()

    fun editGoals(body: EditGoalsBody) {
        viewModelScope.launch {
            editGoals.value = NetworkResult.Loading()
            Util.handleResponse(editGoals, repository.editGoals(body))
        }
    }

    fun getEditGoals(userId: Int) {
        viewModelScope.launch {
            getEditGoals.value = NetworkResult.Loading()
            val t = Util.handleResponse(getEditGoals, repository.getEditGoals(userId))
            sendCode.value = t.code()
        }
    }

    fun validationOnEditGoals(
        workout: Int,
        carbs: Int,
        protein: Int,
        fat: Int,
        overAllCal: String,
        steps: Int,
        water: Int,
        sTargetValue: Int,
        sTargetDate: String,
        lTargetValue: Int,
        lTargetDate: String
    ): Pair<Boolean, String> {
        var result = Pair(true, "")
        if (TextUtils.isEmpty(workout.toString()))
            result = Pair(false, "Please fill the workout")
        else if (TextUtils.isEmpty(carbs.toString()))
            result = Pair(false, "Please select the carbs")
        else if (TextUtils.isEmpty(protein.toString()))
            result = Pair(false, "Please select the protein")
        else if (TextUtils.isEmpty(fat.toString()))
            result = Pair(false, "Please select the fat")
        else if (TextUtils.isEmpty(overAllCal))
            result = Pair(false, "Please fill the overall calories")
        else if (TextUtils.isEmpty(steps.toString()))
            result = Pair(false, "Please fill the steps")
        else if (TextUtils.isEmpty(water.toString()))
            result = Pair(false, "Please fill the water")
        else if (TextUtils.isEmpty(sTargetValue.toString()))
            result = Pair(false, "Please fill the short goal's target value")
        else if (TextUtils.isEmpty(sTargetDate))
            result = Pair(false, "Please select short goal's target date")
        else if (TextUtils.isEmpty(lTargetValue.toString()))
            result = Pair(false, "Please fill the long goal's target value")
        else if (TextUtils.isEmpty(lTargetDate))
            result = Pair(false, "Please select long goal's target date")
        return result
    }
}
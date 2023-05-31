package com.ennovations.fitcrucoach.view_model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ennovations.fitcrucoach.Network.NetworkResult
import com.ennovations.fitcrucoach.repository.Repository
import com.ennovations.fitcrucoach.utility.Util
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuestionnaireViewModel @Inject constructor(private val repository: Repository) : ViewModel() {

    val questionnaire = MutableLiveData<NetworkResult<Any>>()

    fun getHealthQuestionnaire(id: Int) {
        viewModelScope.launch {
            questionnaire.value = NetworkResult.Loading()
            Util.handleResponse(questionnaire, repository.getHealthQuestionnaire(id))
        }
    }
}
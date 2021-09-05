package com.elacqua.soccerleague.ui.teams

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.soccerleague.data.remote.FootballApi
import com.elacqua.soccerleague.data.remote.model.FootballResponse
import com.elacqua.soccerleague.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TeamsViewModel : ViewModel() {

    private val _footballResponse = MutableLiveData<FootballResponse>()
    val footballResponse: LiveData<FootballResponse> = _footballResponse

    init {
        getTeams()
    }

    private fun getTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = FootballApi.getFootballService().getTeams(Constants.BUNDESLIGA_ID)
            _footballResponse.postValue(result)
        }
    }
}
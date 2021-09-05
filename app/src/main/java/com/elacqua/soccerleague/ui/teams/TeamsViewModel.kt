package com.elacqua.soccerleague.ui.teams

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.soccerleague.data.remote.FootballApi
import com.elacqua.soccerleague.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class TeamsViewModel : ViewModel() {

    fun getTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = FootballApi.getFootballService().getTeams(Constants.BUNDESLIGA_ID)
            Timber.e("onCreate: $result")
        }
    }
}
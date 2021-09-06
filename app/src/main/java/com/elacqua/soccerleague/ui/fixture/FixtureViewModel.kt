package com.elacqua.soccerleague.ui.fixture

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.elacqua.soccerleague.data.remote.RemoteRepository
import com.elacqua.soccerleague.data.remote.model.FootballResponse
import com.elacqua.soccerleague.utils.Constants
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FixtureViewModel : ViewModel() {

    private val _footballResponse = MutableLiveData<FootballResponse>()
    val footballResponse: LiveData<FootballResponse> = _footballResponse

    init {
        getTeams()
    }

    private fun getTeams() {
        viewModelScope.launch(Dispatchers.IO) {
            val result = RemoteRepository.getTeams(Constants.BUNDESLIGA_ID)
            _footballResponse.postValue(result)
        }
    }
}
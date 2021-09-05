package com.elacqua.soccerleague.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.elacqua.soccerleague.R
import com.elacqua.soccerleague.data.remote.FootballApi
import com.elacqua.soccerleague.utils.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        CoroutineScope(Dispatchers.IO).launch {
            val result = FootballApi.getFootballService().getTeams(Constants.BUNDESLIGA_ID)
            Timber.e("onCreate: $result")
        }
    }
}
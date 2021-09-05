package com.elacqua.soccerleague.ui

import android.app.Application
import com.elacqua.soccerleague.BuildConfig
import timber.log.Timber

class SoccerLeagueApp : Application() {
    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
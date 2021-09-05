package com.elacqua.soccerleague.data.remote.service

import com.elacqua.soccerleague.data.remote.model.FootballModel
import retrofit2.http.POST
import retrofit2.http.Path

interface FootballService {

    @POST("competitions/{league}/teams")
    suspend fun getTeams(@Path("league") league: String): FootballModel
}
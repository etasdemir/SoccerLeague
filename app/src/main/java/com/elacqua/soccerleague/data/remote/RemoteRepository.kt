package com.elacqua.soccerleague.data.remote

import com.elacqua.soccerleague.data.remote.model.FootballResponse

object RemoteRepository {
    private lateinit var footballResponse: FootballResponse

    suspend fun getTeams(id: String): FootballResponse {
        if (!this::footballResponse.isInitialized
            || footballResponse.competition.id.toString() != id
        ) {
            footballResponse = FootballApi.getFootballService().getTeams(id)
        }
        return footballResponse
    }
}
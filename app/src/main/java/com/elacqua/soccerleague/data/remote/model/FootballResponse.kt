package com.elacqua.soccerleague.data.remote.model


import com.google.gson.annotations.SerializedName

data class FootballResponse(
    @SerializedName("teams")
    val teams: List<FootballTeam> = listOf()
)
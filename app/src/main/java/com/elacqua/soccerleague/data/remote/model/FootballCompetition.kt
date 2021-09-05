package com.elacqua.soccerleague.data.remote.model

import com.google.gson.annotations.SerializedName

data class FootballCompetition(
    @SerializedName("id")
    val id: Int,
    @SerializedName("area")
    val area: FootballTeamArea,
    @SerializedName("name")
    val name: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("plan")
    val plan: String
)
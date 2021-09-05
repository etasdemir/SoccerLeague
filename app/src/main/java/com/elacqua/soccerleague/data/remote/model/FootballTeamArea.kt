package com.elacqua.soccerleague.data.remote.model

import com.google.gson.annotations.SerializedName

data class FootballTeamArea(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
)
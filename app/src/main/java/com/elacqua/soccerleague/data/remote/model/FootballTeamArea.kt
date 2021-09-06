package com.elacqua.soccerleague.data.remote.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FootballTeamArea(
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("name")
    val name: String = ""
) : Parcelable
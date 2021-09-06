package com.elacqua.soccerleague.data.remote.model


import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class FootballTeam(
    @SerializedName("address")
    val address: String = "",
    @SerializedName("area")
    val area: FootballTeamArea = FootballTeamArea(),
    @SerializedName("clubColors")
    val clubColors: String = "",
    @SerializedName("crestUrl")
    val crestUrl: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("founded")
    val founded: Int = 0,
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("lastUpdated")
    val lastUpdated: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("phone")
    val phone: String = "",
    @SerializedName("shortName")
    val shortName: String = "",
    @SerializedName("tla")
    val tla: String = "",
    @SerializedName("venue")
    val venue: String = "",
    @SerializedName("website")
    val website: String = ""
) : Parcelable {
    override fun toString(): String {
        return "team: $shortName"
    }
}
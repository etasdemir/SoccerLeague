package com.elacqua.soccerleague.data.remote.model


import com.google.gson.annotations.SerializedName

data class FootballModel(
    @SerializedName("competition")
    val competition: Competition = Competition(),
    @SerializedName("count")
    val count: Int = 0,
    @SerializedName("filters")
    val filters: Filters = Filters(),
    @SerializedName("season")
    val season: Season = Season(),
    @SerializedName("teams")
    val teams: List<Team> = listOf()
) {
    data class Competition(
        @SerializedName("area")
        val area: Area = Area(),
        @SerializedName("code")
        val code: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("lastUpdated")
        val lastUpdated: String = "",
        @SerializedName("name")
        val name: String = "",
        @SerializedName("plan")
        val plan: String = ""
    ) {
        data class Area(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("name")
            val name: String = ""
        )
    }

    class Filters(
    )

    data class Season(
        @SerializedName("currentMatchday")
        val currentMatchday: Int = 0,
        @SerializedName("endDate")
        val endDate: String = "",
        @SerializedName("id")
        val id: Int = 0,
        @SerializedName("startDate")
        val startDate: String = "",
        @SerializedName("winner")
        val winner: Any = Any()
    )

    data class Team(
        @SerializedName("address")
        val address: String = "",
        @SerializedName("area")
        val area: Area = Area(),
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
    ) {
        data class Area(
            @SerializedName("id")
            val id: Int = 0,
            @SerializedName("name")
            val name: String = ""
        )
    }
}
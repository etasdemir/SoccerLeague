package com.elacqua.soccerleague.core

import com.elacqua.soccerleague.data.remote.model.FootballTeam

object MatchMaker {

    fun getFixtureByWeek(teams: List<FootballTeam>):
            Pair<HashMap<Int, ArrayList<Array<FootballTeam>>>, HashMap<Int, ArrayList<Array<FootballTeam>>>> {

        val firstHalfMatches = ArrayList<Array<FootballTeam>>()
        val secondHalfMatches = ArrayList<Array<FootballTeam>>()
        val weekCount = (teams.size * (teams.size - 1)) / (teams.size / 2)

        for (i in teams.indices) {
            for (j in i.until(teams.size)) {
                if (teams[i] != teams[j]) {
                    firstHalfMatches.add(arrayOf(teams[i], teams[j]))
                    secondHalfMatches.add(arrayOf(teams[j], teams[i]))
                }
            }
        }


        val firstHalfWeeks = generateOneMatchPerWeek(teams.size, weekCount, firstHalfMatches, true)
        val secondHalfWeeks =
            generateOneMatchPerWeek(teams.size, weekCount, secondHalfMatches, false)

        return firstHalfWeeks to secondHalfWeeks
    }

    private fun generateOneMatchPerWeek(
        teamSize: Int,
        weekCount: Int,
        allMatches: ArrayList<Array<FootballTeam>>,
        isFirstHalf: Boolean
    ): HashMap<Int, ArrayList<Array<FootballTeam>>> {
        val weekMap = HashMap<Int, ArrayList<Array<FootballTeam>>>()

        var startIndex = 0
        var endIndex = weekCount / 2
        if (!isFirstHalf) {
            startIndex = weekCount / 2
            endIndex = weekCount
        }
        allMatches@ for (pair in allMatches) {
            var isWeekFound = true
            weeks@ for (i in startIndex until endIndex) {
                if (weekMap[i] == null) {
                    weekMap[i] = ArrayList()
                    isWeekFound = true
                    weekMap[i]!!.add(pair)
                    break
                }

                weekMatches@ for (matches in weekMap[i]!!) {
                    if (matches[0] == pair[0] ||
                        matches[0] == pair[1] ||
                        matches[1] == pair[0] ||
                        matches[1] == pair[1]
                    ) {
                        isWeekFound = false
                        continue@weeks
                    }
                }
                isWeekFound = true
                weekMap[i]!!.add(pair)
                break
            }
            if (!isWeekFound) {
                for (i in (endIndex - 1) downTo startIndex) {
                    if (weekMap[i]!!.size < teamSize / 2) {
                        weekMap[i]!!.add(pair)
                        break
                    }
                }
            }
        }
        return weekMap
    }

}
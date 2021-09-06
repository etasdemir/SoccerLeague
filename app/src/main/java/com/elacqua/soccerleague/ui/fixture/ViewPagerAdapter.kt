package com.elacqua.soccerleague.ui.fixture

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elacqua.soccerleague.data.remote.model.FootballTeam
import com.elacqua.soccerleague.ui.week.WeekFragment

class ViewPagerAdapter(
    fragment: Fragment,
    private val count: Int,
    private val leagueName: String,
    private val weeklyMatchesPair: Pair<HashMap<Int, ArrayList<Array<FootballTeam>>>,
            HashMap<Int, ArrayList<Array<FootballTeam>>>>
) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = count

    override fun createFragment(position: Int): WeekFragment {
        return if (position < count / 2) {
            WeekFragment.newInstance(leagueName, weeklyMatchesPair.first[position]!!)
        } else {
            WeekFragment.newInstance(leagueName, weeklyMatchesPair.second[position]!!)
        }
    }
}
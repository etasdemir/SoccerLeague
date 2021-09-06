package com.elacqua.soccerleague.ui.fixture

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.elacqua.soccerleague.ui.week.WeekFragment

class ViewPagerAdapter(fragment: Fragment, private val count: Int) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount() = count

    override fun createFragment(position: Int) = WeekFragment()
}
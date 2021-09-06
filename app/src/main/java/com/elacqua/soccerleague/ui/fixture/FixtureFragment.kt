package com.elacqua.soccerleague.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.viewpager2.widget.ViewPager2
import com.elacqua.soccerleague.R
import com.elacqua.soccerleague.core.MatchMaker
import com.elacqua.soccerleague.data.remote.model.FootballResponse
import com.elacqua.soccerleague.databinding.FixtureFragmentBinding

class FixtureFragment : Fragment() {

    private val viewModel: FixtureViewModel by viewModels()
    private var binding: FixtureFragmentBinding? = null
    private var weekCount = 0
    private lateinit var pagerAdapter: ViewPagerAdapter
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.footballResponse.observe(viewLifecycleOwner, { response ->
            initViewPager(response)
            listenPageChanges()
        })
    }

    private fun listenPageChanges() {
        pageChangeListener = object : ViewPager2.OnPageChangeCallback() {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                val week = position + 1
                val half = if (position < (weekCount / 2)) {
                    getString(R.string.first_half)
                } else {
                    getString(R.string.second_half)
                }
                binding!!.txtWeekTitle.text = getString(R.string.week_title, week, half)
            }
        }
        binding!!.weekPager.registerOnPageChangeCallback(pageChangeListener)
    }

    private fun initViewPager(response: FootballResponse) {
        val weeklyMatchesPair = MatchMaker.getFixtureByWeek(response.teams)
        val teamCount = response.teamCount.toInt()
        weekCount = (teamCount * (teamCount - 1)) / (teamCount / 2)
        val leagueName = response.competition.name
        pagerAdapter = ViewPagerAdapter(this, weekCount, leagueName, weeklyMatchesPair)
        binding!!.weekPager.apply {
            adapter = pagerAdapter
            setPageTransformer(ForegroundToBackPageTransformer())
            binding!!.txtWeekTitle.text =
                getString(R.string.week_title, 1, getString(R.string.first_half))
        }
    }

    fun goNext() {
        binding!!.weekPager.apply {
            setCurrentItem(currentItem + 1, true)
        }
    }

    fun goBack() {
        binding!!.weekPager.apply {
            setCurrentItem(currentItem - 1, true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FixtureFragmentBinding.inflate(inflater, container, false)
        binding!!.presenter = this
        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding!!.weekPager.unregisterOnPageChangeCallback(pageChangeListener)
        binding = null
    }

}
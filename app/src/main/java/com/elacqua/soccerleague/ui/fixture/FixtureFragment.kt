package com.elacqua.soccerleague.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elacqua.soccerleague.core.MatchMaker
import com.elacqua.soccerleague.data.remote.model.FootballResponse
import com.elacqua.soccerleague.databinding.FixtureFragmentBinding

class FixtureFragment : Fragment() {

    private val viewModel: FixtureViewModel by viewModels()
    private var binding: FixtureFragmentBinding? = null
    private lateinit var pagerAdapter: ViewPagerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.footballResponse.observe(viewLifecycleOwner, { response ->
            initViewPager(response)
            val weeklyMatchesPair = MatchMaker.getFixtureByWeek(response.teams)
        })
    }

    private fun initViewPager(response: FootballResponse) {
        val teamCount = response.teamCount.toInt()
        val weekCount = (teamCount * (teamCount - 1)) / (teamCount / 2)
        pagerAdapter = ViewPagerAdapter(this, weekCount)
        binding!!.weekPager.apply {
            adapter = pagerAdapter
            setPageTransformer(ForegroundToBackPageTransformer())
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FixtureFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}
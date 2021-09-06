package com.elacqua.soccerleague.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.elacqua.soccerleague.data.remote.model.FootballTeam
import com.elacqua.soccerleague.databinding.WeekFragmentBinding
import com.elacqua.soccerleague.utils.Constants
import timber.log.Timber

class WeekFragment : Fragment() {

    private var leagueName: String = "LEAGUE"
    private val matches: ArrayList<Array<FootballTeam>> = ArrayList()
    private val viewModel: WeekViewModel by viewModels()
    private var binding: WeekFragmentBinding? = null
    private lateinit var adapter: WeeklyMatchAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initMatchRv()
        adapter.setMatches(matches)
    }

    private fun initMatchRv() {
        adapter = WeeklyMatchAdapter(leagueName)
        binding!!.rvWeekMatches.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = this@WeekFragment.adapter
            setHasFixedSize(true)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeekFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            leagueName = it.getString(Constants.LEAGUE_NAME_PARAM) ?: "LEAGUE"
            val homeTeams = it.getParcelableArrayList<FootballTeam>(Constants.WEEK_MATCH_PARAM_HOME)
                ?: emptyList()
            val awayTeams = it.getParcelableArrayList<FootballTeam>(Constants.WEEK_MATCH_PARAM_AWAY)
                ?: emptyList()
            val size = Math.max(homeTeams.size, awayTeams.size)
            Timber.e("matches size: $size")
            for (i in 0 until size) {
                matches.add(arrayOf(homeTeams[i], awayTeams[i]))
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(leagueName: String, matches: ArrayList<Array<FootballTeam>>): WeekFragment {
            val homeTeams = ArrayList<FootballTeam>()
            val awayTeams = ArrayList<FootballTeam>()
            for (teams in matches) {
                homeTeams.add(teams[0])
                awayTeams.add(teams[1])
            }
            return WeekFragment().apply {
                arguments = Bundle().apply {
                    putString(Constants.LEAGUE_NAME_PARAM, leagueName)
                    putParcelableArrayList(Constants.WEEK_MATCH_PARAM_HOME, homeTeams)
                    putParcelableArrayList(Constants.WEEK_MATCH_PARAM_AWAY, awayTeams)
                }
            }
        }
    }
}
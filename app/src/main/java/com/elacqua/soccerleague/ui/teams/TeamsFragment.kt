package com.elacqua.soccerleague.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.soccerleague.R
import com.elacqua.soccerleague.databinding.TeamsFragmentBinding

class TeamsFragment : Fragment() {

    private val viewModel by viewModels<TeamsViewModel>()
    private var binding: TeamsFragmentBinding? = null
    private lateinit var adapter: TeamRecyclerAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observerTeams()
    }

    private fun initRecyclerView() {
        adapter = TeamRecyclerAdapter()
        binding!!.rvTeams.apply {
            adapter = this@TeamsFragment.adapter
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext())
        }.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
                if (newState == RecyclerView.SCROLL_STATE_IDLE ||
                    newState == RecyclerView.SCROLL_INDICATOR_TOP
                ) {
                    binding!!.fapDrawFixture.visibility = View.VISIBLE
                }
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0 && binding!!.fapDrawFixture.isShown) {
                    binding!!.fapDrawFixture.visibility = View.INVISIBLE
                }
            }
        })
    }

    private fun observerTeams() {
        viewModel.footballResponse.observe(viewLifecycleOwner, { response ->
            binding!!.txtLeagueName.text =
                getString(R.string.league_name, response.competition.name)
            adapter.setTeamList(response.teams)
        })
    }

    fun navigateToFixtureFragment() {
        findNavController().navigate(R.id.action_teamsFragment_to_fixtureFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = TeamsFragmentBinding.inflate(layoutInflater, container, false)
        binding!!.fragment = this
        return binding!!.root
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
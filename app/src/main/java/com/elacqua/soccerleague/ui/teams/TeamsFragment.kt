package com.elacqua.soccerleague.ui.teams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.elacqua.soccerleague.R
import com.elacqua.soccerleague.databinding.TeamsFragmentBinding

class TeamsFragment : Fragment() {

    private val viewModel by viewModels<TeamsViewModel>()
    private var binding: TeamsFragmentBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

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
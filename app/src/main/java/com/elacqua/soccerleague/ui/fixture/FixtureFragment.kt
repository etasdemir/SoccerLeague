package com.elacqua.soccerleague.ui.fixture

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elacqua.soccerleague.databinding.FixtureFragmentBinding

class FixtureFragment : Fragment() {

    private val viewModel: FixtureViewModel by viewModels()
    private var binding: FixtureFragmentBinding? = null

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
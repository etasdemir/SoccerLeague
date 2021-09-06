package com.elacqua.soccerleague.ui.week

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.elacqua.soccerleague.databinding.WeekFragmentBinding

class WeekFragment : Fragment() {

    private val viewModel: WeekViewModel by viewModels()
    private var binding: WeekFragmentBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = WeekFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

}
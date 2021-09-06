package com.elacqua.soccerleague.ui.week

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.soccerleague.data.remote.model.FootballTeam
import com.elacqua.soccerleague.databinding.MatchListItemBinding
import com.elacqua.soccerleague.utils.Helper

class WeeklyMatchAdapter(private val leagueName: String) :
    RecyclerView.Adapter<WeeklyMatchAdapter.MatchViewHolder>() {

    private val matches: ArrayList<Array<FootballTeam>> = ArrayList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MatchViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = MatchListItemBinding.inflate(inflater, parent, false)
        return MatchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MatchViewHolder, position: Int) {
        holder.onBind(position)
    }

    override fun getItemCount() = matches.size

    fun setMatches(newMatches: ArrayList<Array<FootballTeam>>) {
        matches.clear()
        matches.addAll(newMatches)
        notifyDataSetChanged()
    }

    inner class MatchViewHolder(private val binding: MatchListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind(pos: Int) {
            binding.txtMatchHome.text = matches[pos][0].shortName
            binding.txtMatchAway.text = matches[pos][1].shortName
            binding.txtMatchItemLeagueName.text = leagueName
            Helper.loadImage(matches[pos][0].crestUrl, binding.imgMatchHome)
            Helper.loadImage(matches[pos][1].crestUrl, binding.imgMatchAway)
        }
    }
}
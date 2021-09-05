package com.elacqua.soccerleague.ui.teams

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.elacqua.soccerleague.data.remote.model.FootballTeam
import com.elacqua.soccerleague.databinding.TeamListItemBinding
import com.elacqua.soccerleague.utils.Helper

class TeamRecyclerAdapter : RecyclerView.Adapter<TeamRecyclerAdapter.TeamViewHolder>() {

    private var teams = listOf<FootballTeam>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeamViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = TeamListItemBinding.inflate(inflater, parent, false)
        return TeamViewHolder(binding, teams)
    }

    override fun onBindViewHolder(holder: TeamViewHolder, position: Int) {
        holder.onBind()
    }

    override fun getItemCount() = teams.size

    fun setTeamList(newTeamList: List<FootballTeam>) {
        teams = newTeamList
        notifyDataSetChanged()
    }

    class TeamViewHolder(
        private val binding: TeamListItemBinding,
        private val teams: List<FootballTeam>
    ) :
        RecyclerView.ViewHolder(binding.root) {

        fun onBind() {
            Helper.loadImage(teams[adapterPosition].crestUrl, binding.imgTeamLogo)
            binding.txtTeamName.text = teams[adapterPosition].shortName
            binding.txtTeamStadium.text = teams[adapterPosition].venue
        }
    }
}
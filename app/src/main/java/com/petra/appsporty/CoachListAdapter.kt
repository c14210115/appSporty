package com.petra.appsporty

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView


class CoachListAdapter (
    private val coachList: List<Coach>,
    private val onItemClick: (Coach) -> Unit,
    private val onFavoriteClick: (Coach) -> Unit
) : RecyclerView.Adapter<CoachListAdapter.CoachViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coach, parent, false)
        return CoachViewHolder(view)
    }

    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        val coach = coachList[position]
        holder.bind(coach)
        holder.itemView.setOnClickListener { onItemClick(coach) }
        holder.btnFavorite.setOnClickListener { onFavoriteClick(coach) }
    }

    override fun getItemCount(): Int = coachList.size

    inner class CoachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCoachName: TextView = itemView.findViewById(R.id.tvCoachName)
        private val tvCoachLocation: TextView = itemView.findViewById(R.id.tvCoachLocation)
        private val tvCoachAge: TextView = itemView.findViewById(R.id.tvCoachAge)
        private val tvCoachPrice: TextView = itemView.findViewById(R.id.tvCoachPrice)
        val btnInfo: Button = itemView.findViewById(R.id.btnInfo)
        val btnFavorite: Button = itemView.findViewById(R.id.btnFavorite)

        fun bind(coach: Coach) {
            tvCoachName.text = "Name: ${coach.name}"
            tvCoachLocation.text = "Location: ${coach.location}"
            tvCoachAge.text = "Age: ${coach.age}"
            tvCoachPrice.text = "Price: ${coach.price}"

        }
    }
}
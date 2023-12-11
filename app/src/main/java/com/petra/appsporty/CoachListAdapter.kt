package com.petra.appsporty

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView

class CoachListAdapter (
    private val coachList: ArrayList<Coach>,
//    private val onItemClick: (Coach) -> Unit,
//    private val onFavoriteClick: (Coach) -> Unit
) : RecyclerView.Adapter<CoachListAdapter.CoachViewHolder>(){

    //untuk click
    private lateinit var onItemClickCallback : OnItemClickCallback
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoachViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_coach, parent, false)
        return CoachViewHolder(view)
    }

    interface OnItemClickCallback {

        fun onItemClicked(data : Coach)
        //        fun onItemClicked(data : Map<String, String>)
        fun delData(pos: Int)

    }

    fun setOnItemClickCallback(onItemClickCallback : OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }
    override fun onBindViewHolder(holder: CoachViewHolder, position: Int) {
        val coach = coachList[position]

        //membaca image
        val context = holder.itemView.context
        val imageRes = context.resources.getIdentifier(
            coach.photo, "drawable", context.packageName
        )
        holder.imgCoachImage.setImageResource(imageRes)
        holder.imgCoachImage.scaleType
        holder.bind(coach)


        holder.btnFavorite.setOnClickListener{
////            Toast.makeText(holder.itemView.context, pahlawan.nama, Toast.LENGTH_LONG).show()
//            onItemClickCallback.onItemClicked(listPahlawan[position])
        }

        holder.btnDetail.setOnClickListener{

        }
    }

    override fun getItemCount(): Int {
        return coachList.size
    }

    inner class CoachViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imgCoachImage: ImageView = itemView.findViewById(R.id.imgCoachImage)
        private val tvCoachName: TextView = itemView.findViewById(R.id.tvCoachName)
        private val tvCoachLocation: TextView = itemView.findViewById(R.id.tvCoachLocation)
        private val tvCoachAge: TextView = itemView.findViewById(R.id.tvCoachAge)
        private val tvCoachPrice: TextView = itemView.findViewById(R.id.tvCoachPrice)
        private val tvCoachCategory: TextView = itemView.findViewById(R.id.tvCoachCategory)
        private val tvCoachRating: TextView = itemView.findViewById(R.id.tvCoachRating)

        val btnDetail: Button = itemView.findViewById(R.id.btnInfo)
        val btnFavorite: ImageButton = itemView.findViewById(R.id.btnFavorite)

        fun bind(coach: Coach) {
            tvCoachName.text = coach.name
            tvCoachCategory.text = coach.category
            tvCoachLocation.text = coach.location
            tvCoachAge.text = coach.age
            tvCoachPrice.text = coach.price.toString()
            tvCoachRating.text = coach.rating

            if(coach.isfav == "True"){
                btnFavorite.setBackgroundResource(R.drawable.fullfav)
            }


        }
    }
}
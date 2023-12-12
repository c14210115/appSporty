package com.petra.appsporty

import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

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

        fun onItemClicked(pos: Int)
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

        holder.bind(coach)


        holder.btnFavorite.setOnClickListener{
            onItemClickCallback.onItemClicked(position)
            Log.d("FAV", "masuk fav")



        }
        //Anton : Ini buat ke halaman detail
        holder.btnDetail.setOnClickListener{
            val coachData = coachList[position]

            val bundle = Bundle().apply {
                putString("nama", coachData.name)
                putString("gambar", coachData.photo)
                putString("harga", coachData.price)
                putString("cabor", coachData.category)
                putString("umur", coachData.age)
                putString("rate", coachData.rating)
                putString("note", coachData.notes)
                putString("ig", coachData.instagram)
                putString("wa", coachData.telp)
                putString("lokasi", coachData.location)
            }
            val detailCoachFragment = fragment_detail_coach.newInstance("data1","data2")
            detailCoachFragment.arguments = bundle

            // Memulai transaksi untuk pindah ke halaman detail
            val transaction = (holder.itemView.context as AppCompatActivity).supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragmentContainerView, detailCoachFragment)
            transaction.addToBackStack(null)
            transaction.commit()
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
                Log.d("isFAV", "favorite")
                btnFavorite.setImageResource(R.drawable.fullfav)

            } else{
                btnFavorite.setImageResource(R.drawable.borderfav)

            }


        }
    }
}
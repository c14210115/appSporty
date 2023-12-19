package com.petra.appsporty

import android.content.Intent
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext


// buat func cari coach dari list sesuai id
fun getCoachFromID(idCoach : String, listCoach : ArrayList<Coach>) : Coach?{
    for (coach in listCoach){
        if (coach.id == idCoach){
            return coach
        }
    }
    return null
}

class OrderListAdapter (
    private val listDataOrder : ArrayList<Order>
) : RecyclerView.Adapter<OrderListAdapter.ListViewHolder>(){
    //untuk click
    private lateinit var onItemClickCallback : OrderListAdapter.OnItemClickCallback

    // buat interface nya
    interface OnItemClickCallback {
        fun onBtnDetailClicked(dataOrder : Order, dataCoach : Coach)
    }

    // bind ke dalam recycle view template item dalam list
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var  _namaCoach: TextView = itemView.findViewById(R.id.tvOrder_namaCoach)
        var _lapanganOrder : TextView = itemView.findViewById(R.id.tvOrder_lapanganYangDiPesan)
        var _jamDipesan : TextView = itemView.findViewById(R.id.tvOrder_jamPesanLapangan)
        var _tanggalDipesan : TextView = itemView.findViewById(R.id.tvOrder_tanggalDiPesan)
        var _totalHargaOrder : TextView = itemView.findViewById(R.id.tvOrder_totalHarga)
        var _btnDetailOrder : Button = itemView.findViewById(R.id.btnDetailOrder)
        var _btnStatus : Button = itemView.findViewById(R.id.btnStatus)
    }

    fun SetData(listBaru : ArrayList<Order>){
        listDataOrder.clear()
        listDataOrder.addAll(listBaru)
        notifyDataSetChanged()
    }
    fun setOnItemClickCallback(onItemClickCallback : OrderListAdapter.OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        // buat viewholder
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_order, parent, false) // set di activity
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        // masukin data kedalam viewnya
        var dataOrder = listDataOrder[position]

        // asycronous juga
        CoroutineScope(Dispatchers.IO).async {
            // coba ambil ini dulu dari database coach
            var dataCoach = getCoachFromID(dataOrder.idCoach, fragment_home.listCoach)

            // jalanin bind kalo udah selesai
            withContext(Dispatchers.Main){
                // setting data ke view
                holder._namaCoach.setText(dataCoach!!.name)
                holder._lapanganOrder.setText(dataOrder.lapanganDipesan)
                holder._totalHargaOrder.setText("Rp. " + dataOrder.totalHarga.toString())
                holder._jamDipesan.setText(dataOrder.jamDipesan) // kalo sempat di format
                holder._tanggalDipesan.setText(dataOrder.tanggalDipesan)

                // set color sesuai status button (warna button)
                holder._btnStatus.setBackgroundColor(Color.parseColor(dataOrder.status.colorBtn)); // mungkin ga keluar
                holder._btnStatus.setText(dataOrder.status.nama)
                holder._btnStatus.isClickable = false // buat ga bisa di klik dan event action
                holder._btnStatus.isEnabled = false

                Log.d("DB COACH",  "data coach : $dataCoach")

                // buat holder untuk action button detail
                holder._btnDetailOrder.setOnClickListener {
                    onItemClickCallback.onBtnDetailClicked(dataOrder, dataCoach )
                }
            }

        }
    }

    override fun getItemCount(): Int {
        // buat tau banyak view yang dibuat
        return listDataOrder.size
    }
}
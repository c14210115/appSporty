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


// buat func ambil database
fun getCoachFromDatabase(idCoach : String) : Coach? {
    val dbCoach = Firebase.firestore.collection("tbListCoach")
    var coach : Coach? = null
    dbCoach.get()
        .addOnSuccessListener { listCoach ->
            for (coachData in listCoach) {
                // cek misal sama idnya
                if (idCoach == coachData.id) {
                    // simpan dulu sebagai coach
                    var id = coachData.get("id").toString()
                    var nama = coachData.get("name").toString()
                    var umur = coachData.get("age").toString()
                    var kategori = coachData.get("category").toString()
                    var lapangan = coachData.get("facility").toString()
                    var isfav = coachData.get("isFav").toString()
                    var lokasi = coachData.get("location").toString()
                    var catatan = coachData.get("notes").toString()
                    var foto = coachData.get("photo").toString()
                    var harga = coachData.get("price").toString()
                    var rating = coachData.get("rating").toString()
                    var telp = coachData.get("telp").toString()
                    var time = coachData.get("time").toString()
                    var trained = coachData.get("trained").toString()
                    var ig = coachData.get("instagram").toString()

                    // masukin sebagai class coach
                    var getDataCoach = Coach(
                        id,
                        foto,
                        nama,
                        kategori,
                        lokasi,
                        umur,
                        harga,
                        isfav,
                        rating,
                        trained,
                        catatan,
                        telp,
                        ig,
                        lapangan,
                        time
                    )

                    coach = getDataCoach
                }
            }
        }
    Log.d("TAG COACH DATA", "HASIL COACH ${coach!!.name}")
    return coach
}

class OrderListAdapter (
    private val listDataOrder : ArrayList<Order>
) : RecyclerView.Adapter<OrderListAdapter.ListViewHolder>(){
    //untuk click
    private lateinit var onItemClickCallback : OrderListAdapter.OnItemClickCallback
    interface OnItemClickCallback {
        fun onBtnDetailClicked()
    }
    // bind ke dalam recycle view template item dalam list
    inner class ListViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        var  _namaCoach: TextView = itemView.findViewById(R.id.tvOrder_namaCoach)
        var _lapanganOrder : TextView = itemView.findViewById(R.id.tvOrder_lapanganYangDiPesan)
        var _jamDipesan : TextView = itemView.findViewById(R.id.tvOrder_jamPesanLapangan)
        var _totalHargaOrder : TextView = itemView.findViewById(R.id.tvOrder_totalHarga)
        var _btnDetailOrder : Button = itemView.findViewById(R.id.btnDetailOrder)
        var _btnStatus : Button = itemView.findViewById(R.id.btnStatus)
    }

    fun SetData(listBaru : ArrayList<Order>){
        listDataOrder.clear()
        listDataOrder.addAll(listBaru)
        notifyDataSetChanged()
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
            var dataCoach = getCoachFromDatabase(dataOrder.idCoach)

            // jalanin bind kalo udah selesai
            withContext(Dispatchers.Main){
                // setting data ke view
                holder._namaCoach.setText(dataCoach!!.name)
                holder._lapanganOrder.setText(dataOrder.lapanganDipesan)
                holder._totalHargaOrder.setText(dataOrder.totalHarga)
                holder._jamDipesan.setText(dataOrder.jamDipesan) // kalo sempat di format

                // set color sesuai status button (warna button)
                holder._btnStatus.setBackgroundColor(Color.parseColor(dataOrder.status.colorBtn)); // mungkin ga keluar
                holder._btnStatus.isClickable = false // buat ga bisa di klik dan event action
                holder._btnStatus.isEnabled = false


                // kalo diklik di kasih action ke view detail
                holder._btnDetailOrder.setOnClickListener {
                    // kirim data order dan data coach
                    // pindah page
                }
            }
        }
    }

    override fun getItemCount(): Int {
        // buat tau banyak view yang dibuat
        return listDataOrder.size
    }
}
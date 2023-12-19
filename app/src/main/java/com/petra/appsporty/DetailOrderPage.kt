package com.petra.appsporty

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import org.checkerframework.dataflow.qual.TerminatesExecution
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailOrderPage : AppCompatActivity() {
    companion object{
        var dataOrder : Order? = null
        var dataCoach : Coach? = null
    }

    lateinit var _tvDetail_tanggalPesanan: TextView
    lateinit var _tvDetail_kategoriCoach : TextView

    lateinit var _tvDetail_namaCoach : TextView
    lateinit var _tvDetail_lapanganDipesan : TextView
    lateinit var _tvDetail_jamDipesan : TextView
    lateinit var _tvDetail_tanggalDipesan : TextView
    lateinit var _tvDetail_totalHarga : TextView
    lateinit var _btnBatal : Button

    fun convertOrderIntoHashmap(order: Order) : HashMap<String, Any> {
        val orderMap = hashMapOf(
            "idOrder" to  order.id,
            "usernameOrder" to order.username,
            "idCoachOrder" to order.idCoach,
            "timeOrder" to order.jamDipesan,
            "dateTrainingOrder" to order.tanggalDipesan,
            "facilityOrder" to order.lapanganDipesan,
            "dateOrder" to order.waktuPesan,
            "priceOrder" to order.totalHarga,
            "statusOrder" to order.status.status
        ) as HashMap<String, Any>
        return orderMap
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_order_page)

        // masukin reference textview
        _tvDetail_tanggalPesanan = findViewById(R.id.tvDetail_tanggalPesanan)
        _tvDetail_kategoriCoach = findViewById(R.id.tvDetail_kategori)
        _tvDetail_namaCoach = findViewById(R.id.tvDetail_namaCoach)
        _tvDetail_lapanganDipesan = findViewById(R.id.tvDetail_lapanganDipesan)
        _tvDetail_jamDipesan = findViewById(R.id.tvDetail_lapanganDipesan)
        _tvDetail_tanggalDipesan = findViewById(R.id.tvDetail_tanggalDipesan)
        _tvDetail_totalHarga = findViewById(R.id.tvDetail_totalHarga)
        _btnBatal = findViewById(R.id.btnBatalPesanan)

        // bind data dengan textview
        _tvDetail_tanggalPesanan.text = dataOrder!!.waktuPesan
        _tvDetail_kategoriCoach.text  = dataCoach!!.category
        _tvDetail_namaCoach.text = dataCoach!!.name
        _tvDetail_lapanganDipesan.text = dataOrder!!.lapanganDipesan
        _tvDetail_jamDipesan.text = dataOrder!!.jamDipesan
        _tvDetail_tanggalDipesan.text = dataOrder!!.tanggalDipesan
        _tvDetail_totalHarga.text = "Rp. " + dataOrder!!.totalHarga

        // cek action button
        // cek status pesanan
        // misal sedang berlangsung
        if (dataOrder!!.status.status == 0){
            var today = Date()
            // Membuat objek SimpleDateFormat
            val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
            // Memformat tanggal menjadi string
            val todayFormat = dateFormat.format(today)

            // maka bisa di cek tanggal sekarang misal sudah sama harinya ga perlu nampilin buttonnya
            if (dataOrder!!.tanggalDipesan == todayFormat){
                _btnBatal.isVisible = false // ga bisa di batal , dihilangin btnnya
            }
            else {
                // bisa batal
                _btnBatal.isVisible = true
            }
        }
        else {
            // jika udah selesai / dibatal maka hilangin button btn batalnya
            _btnBatal.isVisible = false
        }

        // kasih actiond dalam button batal
        _btnBatal.setOnClickListener {
            // cek konfirmasi
            AlertDialog.Builder(this@DetailOrderPage)
                .setTitle("KONFIRMASI BATAL PESANAN")
                .setMessage("APAKAH ANDA YAKIN INGIN MEMBATALKAN PESANAN?")
                .setPositiveButton(
                    "YA",
                    DialogInterface.OnClickListener{ dialog, which ->
                        // kalo ini di klik maka set data sekarang menjadi dibatalkan
                        dataOrder!!.status = StatusOrder.CANCELED

                        // buat hashmapnya
                        // update ke database
                        val dbOrderUsers = Firebase.firestore.collection("Users").document(dataOrder!!.username).collection("tbOrder")
                        dbOrderUsers.document(dataOrder!!.id).set(convertOrderIntoHashmap(dataOrder!!))

                        // balik ke halaman sebelumnya?
                        Toast.makeText(this@DetailOrderPage,"Pesanan Dibatalkan", Toast.LENGTH_SHORT).show()
                        finish() // posisi pindah onresume?
                    }
                )
                .setNegativeButton(
                    "TIDAK",
                    DialogInterface.OnClickListener{ dialog, which ->
                        Toast.makeText(this@DetailOrderPage,
                            "Pesanan tidak jadi Dibatalkan",
                            Toast.LENGTH_SHORT).show()
                    }
                )
                .show()
        }
    }
}
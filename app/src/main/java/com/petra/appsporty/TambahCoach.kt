package com.petra.appsporty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore

class TambahCoach : AppCompatActivity() {
    lateinit var _etId : EditText
    lateinit var _etNama : EditText
    lateinit var _etLapangan : EditText
    lateinit var _etKategori : EditText
    lateinit var _etLokasi : EditText
    lateinit var _etUmur : EditText
    lateinit var _etHarga : EditText
    lateinit var _etNote : EditText
    lateinit var _etIg : EditText
    lateinit var _etTelp : EditText
    lateinit var _etWaktu : EditText
    lateinit var _btnSubmit : Button

    val dbCoach = Firebase.firestore
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_coach)


//        // binding
//        _etId = findViewById(R.id.et_id)
        _etNama = findViewById(R.id.etNamaCoach)
        _etLapangan = findViewById(R.id.etLapangan)
        _etKategori = findViewById(R.id.etKategori)
        _etLokasi = findViewById(R.id.etLokasi)
        _etUmur = findViewById(R.id.etUmur)
        _etHarga = findViewById(R.id.et_Harga)
        _etNote = findViewById(R.id.et_detail)
        _etIg = findViewById(R.id.et_ig)
        _etTelp = findViewById(R.id.et_telp)
        _etWaktu = findViewById(R.id.et_waktu)
        _btnSubmit = findViewById(R.id.btn_submitCoach)

        // action button
        _btnSubmit.setOnClickListener {
            // tambah ke firebase
            dbCoach.collection("tbListCoach").get().
            addOnSuccessListener { documents ->
                val idOrder = documents.size()+1
                //buat hasmap ordernya
                val coachMap = hashMapOf(
                    "id" to "c${idOrder}",
                    "photo" to  "virganatasantoso",
                    "name" to _etNama.text.toString(),
                    "category" to _etKategori.text.toString(),
                    "location" to _etLokasi.text.toString(),
                    "age" to _etUmur.text.toString(),
                    "price" to _etHarga.text.toString(),
                    "isFav" to "false",
                    "rating" to  "4.0",
                    "trained" to "10",
                    "notes" to _etNote.text.toString(),
                    "telp" to _etTelp.text.toString(),
                    "instagram" to _etIg.text.toString(),
                    "facility" to _etLapangan.text.toString(),
                    "time" to _etWaktu.text.toString(),
                )
                dbCoach.collection("tbListCoach").document("c${idOrder}").set(coachMap)

                // pindah
                finish()
            }
        }
    }
}
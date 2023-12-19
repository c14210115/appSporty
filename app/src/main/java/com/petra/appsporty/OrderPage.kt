package com.petra.appsporty

import android.app.DatePickerDialog
import android.content.Intent
import android.content.res.ColorStateList
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.children
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import java.lang.Integer.parseInt
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class OrderPage : AppCompatActivity() {
    private lateinit var calendar: Calendar
    private lateinit var _tvDate: TextView
    private lateinit var today: Date
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_page)

        var username: String? = null
        val dbOrder = Firebase.firestore

        val _tvCoach = findViewById<TextView>(R.id.tvCoachOrder)
        val _tvPrice = findViewById<TextView>(R.id.tvPriceOrder)
        _tvDate = findViewById(R.id.edtDateOrder)
        val _lnFacilities = findViewById<LinearLayout>(R.id.lnFacilities)
        val _lnHours = findViewById<LinearLayout>(R.id.lnHours)
        val _tvTotalPrice = findViewById<TextView>(R.id.tvTotalPriceOrder)
        val _btnSubmit = findViewById<Button>(R.id.btnSubmitOrder)
        val _btnBack = findViewById<ImageButton>(R.id.btnBackDetailCoach)

        //variabel textview hitung harga dan simpan lapangan
        var lapangan: String = null.toString()
        var simpanJam: MutableSet<String> = mutableSetOf()
        var isClickFacility: Boolean = false
        var isClickHours: Boolean = false
        var totalPrice = 0
        var isHours: Boolean = false

        //menggunakan fungsi tombol back
        _btnBack.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        //DATE
        calendar = Calendar.getInstance()
        today = Date()
        // Membuat objek SimpleDateFormat
        val dateFormat = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        // Memformat tanggal menjadi string
        val formattedDate = dateFormat.format(today)

        // jika edt ditekan maka menjalankan fungsi datepicker
        _tvDate.setOnClickListener{

            showDatePicker()
        }


        //mengambil data dari fragment detail coach
        val _dataCoach = intent.getParcelableExtra(dataCoach, Coach::class.java)
        //masukan data ke tampilan
        _tvCoach.text = "   ${_dataCoach!!.name}"
        _tvPrice.text = "  Rp. ${_dataCoach.price}"

        //split data string fasilitas dan jamnya
        val valuesHour: List<String> = _dataCoach.time.split("|")
        val valueFacility: List<String> = _dataCoach.facility.split("|")

        //variabel margin untuk generate textview
        val leftMargin = 0
        val topMargin = 20
        val rightMargin = 0
        val bottomMargin = 20

        val widthInPixels = 400
        val heightInPixels = 60
        // set parameter dari linear layout
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
        layoutParams.width = widthInPixels
        layoutParams.height = heightInPixels
        //SET LINEAR LAYOUT
        _lnHours.gravity = CENTER_HORIZONTAL
        _lnFacilities.gravity = CENTER_HORIZONTAL

        //variable warna
        val color = "#28D5F3"
        val colorInt = Color.parseColor(color)
        val colorStateList = ColorStateList.valueOf(colorInt)

        //generate textview
        for ((c1, hour) in valuesHour.withIndex()) {
            val textHour = TextView(this)
            textHour.text = hour
            textHour.id = c1
            textHour.tag = hour
            Log.d("jamid", "${textHour.id}")
            textHour.isClickable = true
            textHour.textAlignment = TEXT_ALIGNMENT_CENTER

            // masukan layout parameternya
            textHour.layoutParams = layoutParams

            textHour.setBackgroundResource(R.drawable.roundedcornerbar)
            _lnHours.addView(textHour)

            //ketika ditekan textviewnya

            textHour.setOnClickListener {
                // loop untuk cek data, agar textview bisa ditekan sekali dan dihapus sesuai textview yang ditekan
                for( i in simpanJam){
                    if(i == textHour.tag.toString()){
                        //jika true maka dia sudah di tekan dan bisa di hapus
                        isHours = true
                        isClickHours = true
                        break
                    }
                    isHours = false
                }

                if (isClickHours && isHours) {
                    textHour.backgroundTintList =
                        ContextCompat.getColorStateList(this, android.R.color.transparent)
                    //hapus data jam yang dipilih
                    simpanJam.remove(textHour.tag.toString())
                    isClickHours = false
                    Log.d("simpanjam", "$simpanJam")
                } else {
                    textHour.backgroundTintList = colorStateList
                    //masukan data jam yang dipilih
                    simpanJam.add(textHour.tag.toString())
                    Log.d("simpanjam", "$simpanJam")
                    isClickHours = true
                    //count untuk menghitung harga total nantinya
                }
                //setharga berdasarkan banyak sesi yang dipilih
                totalPrice = parseInt(_dataCoach.price) * simpanJam.size
                _tvTotalPrice.text = "TOTAL: Rp. $totalPrice"
            }

        }

        for ((c2, facility) in valueFacility.withIndex()) {
            val textFacility = TextView(this)
            textFacility.text = facility
            textFacility.id = c2
            textFacility.tag = facility
            Log.d("halo", "${textFacility.tag}")
            textFacility.isClickable = true
            textFacility.textAlignment = TEXT_ALIGNMENT_CENTER

            //masukan data parameternya
            textFacility.layoutParams = layoutParams

            textFacility.setBackgroundResource(R.drawable.roundedcornerbar)
            _lnFacilities.addView(textFacility)

            textFacility.setOnClickListener {
                if (isClickFacility && lapangan == textFacility.tag) {
                    lapangan = null.toString()
                    Log.d("testTag", lapangan)
                    textFacility.backgroundTintList =
                        ContextCompat.getColorStateList(this, android.R.color.transparent)
                    isClickFacility = false
                } else {
                    //loop untuk hanya bisa 1 fasilitas yang ditekan
                        if(lapangan != null.toString()) {
                            for (textView in _lnFacilities.children) {
                                //cek semua textview dalam linear layout untuk mengganti bg textview lain
                                if (textView is TextView && textView.tag == lapangan) {
                                    textView.backgroundTintList =
                                        ContextCompat.getColorStateList(
                                            this,
                                            android.R.color.transparent
                                        )
                                }
                            }
                        }
                    lapangan = textFacility.tag.toString()
                    Log.d("testTag", lapangan)
                    textFacility.backgroundTintList = colorStateList
                    isClickFacility = true
                }
            }
        }
        //submit order
        _btnSubmit.setOnClickListener {
            //cek input lapangan dan jam
            if (lapangan != null.toString() && simpanJam.isNotEmpty()
                && _tvDate.text.toString().isNotEmpty()) {
                val main = MainActivity()
                username = main.getMyUsername()

                dbOrder.collection("users").document(username.toString())
                    .collection("tbOrder").get().
                    addOnSuccessListener { documents ->
                        val idOrder = documents.size()
                        //buat hasmap ordernya
                        val orderMap = hashMapOf(
                            "idOrder" to  idOrder.toString(),
                            "usernameOrder" to username,
                            "idCoachOrder" to _dataCoach.id,
                            "timeOrder" to simpanJam.toString(),
                            "dateTrainingOrder" to _tvDate.text.toString(),
                            "facilityOrder" to lapangan,
                            "dateOrder" to formattedDate.toString(),
                            "priceOrder" to totalPrice,
                            "statusOrder" to StatusOrder.ONGOING.status,
                        )
                        //masukan ke database
                        dbOrder.collection("users").document(username.toString())
                            .collection("tbOrder").document(idOrder.toString()).set(orderMap)
                    }

                Toast.makeText(
                    this@OrderPage,
                    "Order Successful",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                Toast.makeText(
                    this@OrderPage,
                    "Facility or Trainning Hour or Date is not Entered",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
    companion object{
        val dataCoach= "getCoach"
    }

    private fun showDatePicker() {
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        // menggunakan alart dialog untuk memunculkan date dialog
        val datePickerDialog = DatePickerDialog(
            this,
            { _, selectedYear, selectedMonth, selectedDay ->
                calendar.set(selectedYear, selectedMonth, selectedDay)
                updateDateInView()
            },
            year, month, day
        )
        datePickerDialog.datePicker.minDate = calendar.timeInMillis
        datePickerDialog.show()
    }

    private fun updateDateInView() {
        val myFormat = "dd-MM-yyyy" // sama dengan format date di home
        val sdf = SimpleDateFormat(myFormat, Locale.US)
        _tvDate.text = sdf.format(calendar.time)
    }
}
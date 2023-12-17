package com.petra.appsporty

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity.CENTER_HORIZONTAL
import android.view.View.TEXT_ALIGNMENT_CENTER
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.TextView

class OrderPage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_page)

        val _tvCoach = findViewById<TextView>(R.id.tvCoachOrder)
        val _tvPrice = findViewById<TextView>(R.id.tvPriceOrder)
        val _edtDate = findViewById<EditText>(R.id.edtDateOrder)
        val _lnFacilities = findViewById<LinearLayout>(R.id.lnFacilities)
        val _lnHours= findViewById<LinearLayout>(R.id.lnHours)
        val _tvTotalPrice = findViewById<TextView>(R.id.tvTotalPriceOrder)
        val _btnSubmit= findViewById<Button>(R.id.btnSubmitOrder)
        val _btnBack = findViewById<ImageButton>(R.id.btnBackDetailCoach)

        _btnBack.setOnClickListener{
            //menggunakan fungsi tombol back
            onBackPressed()
        }

        //mengambil data dari fragment detail coach
        val _dataCoach = intent.getParcelableExtra(dataCoach, Coach::class.java)

        //masukan data ke tampilan
        _tvCoach.text = "   ${_dataCoach!!.name}"
        _tvPrice.text = "  Rp. ${_dataCoach.price}"

        //split data string fasilitas dan jamnya
        val valuesHour: List<String> = _dataCoach.time.split("|")
        val valueFacility: List<String> = _dataCoach.facility.split("|")

        Log.d("tes jam", "$valuesHour")


        //variabel margin untuk generate textview
        val leftMargin = 0
        val topMargin = 15
        val rightMargin= 0
        val bottomMargin = 15
        // set parameter dari linear layout
        val layoutParams = LinearLayout.LayoutParams(
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )
        layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)

        _lnHours.gravity = CENTER_HORIZONTAL
        _lnFacilities.gravity = CENTER_HORIZONTAL
        //generate textview
        for((c1, hour) in valuesHour.withIndex()){
            val textHour = TextView(this)
            textHour.text = hour
            textHour.id = c1
            textHour.tag = hour
            Log.d("jam", "${textHour.tag}")
            textHour.isClickable=true
            textHour.textAlignment = TEXT_ALIGNMENT_CENTER

           // masukan layout parameternya
            textHour.layoutParams = layoutParams

            _lnHours.addView(textHour)
        }

        for((c2, facility) in valueFacility.withIndex()){
            val textFacility = TextView(this)
            textFacility.text = facility
            textFacility.id = c2
            textFacility.tag= facility
            Log.d("halo", "${textFacility.tag}")
            textFacility.isClickable=true
            textFacility.textAlignment = TEXT_ALIGNMENT_CENTER

            //masukan data parameternya
            layoutParams.setMargins(leftMargin, topMargin, rightMargin, bottomMargin)
            textFacility.layoutParams = layoutParams

            _lnFacilities.addView(textFacility)
        }



    }


    companion object{
        val dataCoach= "getCoach"
    }
}
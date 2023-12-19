package com.petra.appsporty

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date


data class Order(
    val id : String,
    val username: String, // tinggal cari punya siapa di database
    val idCoach : String, // tinggal cari di database
    val jamDipesan : String,
    val tanggalDipesan: String,
    val lapanganDipesan : String,
    val waktuPesan : String,
    val totalHarga : Int,
    var status : StatusOrder
)

@Parcelize
enum class StatusOrder(val nama : String, val status : Int, val colorBtn : String) : Parcelable {
    ONGOING("Berlangsung",0, "#B8B8A9"),
    DONE("Selesai",1, "#5FA545"),
    CANCELED("Dibatalkan",2, "#B62C2C");
}

fun getStatus(statusInt : Int) : StatusOrder{
    if (statusInt == 0){
        return StatusOrder.ONGOING
    }
    else if (statusInt == 1){
        return StatusOrder.DONE
    }
    else {
        return StatusOrder.CANCELED
    }
}

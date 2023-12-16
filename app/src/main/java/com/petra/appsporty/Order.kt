package com.petra.appsporty

import android.os.Parcel
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.Date


data class Order(
    val username: String, // tinggal cari punya siapa di database
    val idCoach : String, // tinggal cari di database
    val jamDipesan : String,
    val tanggalDipesan: String,
    val waktuPesan : String,
    val status : StatusOrder
)

@Parcelize
enum class StatusOrder(val nama : String,val status : Int) : Parcelable {

    ONPROGRESS("Sedang Diproses",0),
    DONE("Selesai",1),
    CANCELED("Dibatalkan",2);
}

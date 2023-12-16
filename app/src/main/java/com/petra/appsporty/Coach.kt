package com.petra.appsporty

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Coach(
    val id: String,
    val photo: String,
    val name: String,
    val category: String,
    val location: String,
    val age: String,
    val price: String,
    val isfav: String,
    val rating: String,
    val trained: String,
    val notes: String,
    val telp: String,
    val instagram: String,
    val facility: String,
    val time: String,
//    var isFavorite: Boolean = false
) : Parcelable

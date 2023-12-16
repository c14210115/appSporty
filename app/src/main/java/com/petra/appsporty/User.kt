package com.petra.appsporty

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val userName: String,
    val userAge: String,
    val userGender: String,
    val userTelp: String,
    val userEmail: String,
    val usercategory: String,
    val userNotes: String,
    val userPw : String,
// very not safe
): Parcelable

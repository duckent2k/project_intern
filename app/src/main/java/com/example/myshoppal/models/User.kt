package com.example.myshoppal.models
import kotlinx.android.parcel.Parcelize
import android.os.Parcelable
import java.io.Serializable

data class User(
    val id: String = "",
    val firstname: String = "",
    val lastname: String = "",
    val email: String = "",
    val image: String = "",
    val mobile: String  = "",
    val gender: String = "",
    val profileCompleted: Int = 0
) : Serializable


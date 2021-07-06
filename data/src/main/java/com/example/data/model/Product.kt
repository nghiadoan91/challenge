package com.example.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Product(
    @Json(name = "name")
    val name: String = "",
    @Json(name = "price")
    val price: Double = 0.0,
    @Json(name = "imageUrl")
    val imageUrl: String = ""
) : Parcelable
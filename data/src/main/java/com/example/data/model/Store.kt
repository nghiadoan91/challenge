package com.example.data.model

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class Store(
    @Json(name = "name")
    val name: String = "",
    @Json(name = "rating")
    val rating: Double = 0.0,
    @Json(name = "openingTime")
    val openingTime: String = "",
    @Json(name = "closingTime")
    val closingTime: String = ""
) : Parcelable
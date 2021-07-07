package com.example.data.model.request

import android.os.Parcelable
import com.example.data.model.Product
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
data class OrderRequest(
    @Json(name = "products")
    val products: List<Product> = emptyList(),
    val address: String = ""
) : Parcelable
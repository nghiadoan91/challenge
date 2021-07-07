package com.example.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ProductViewState(
    val id: Int = 0,
    val name: String = "",
    val price: Double = 0.0,
    val imageUrl: String = "",
    val addedQty: Int = 0
) : Parcelable
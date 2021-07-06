package com.example.opnchallenge.base.extension

import android.content.Context
import android.widget.ImageView
import com.example.opnchallenge.base.GlideApp

fun ImageView.loadUrl(
    context: Context,
    imageUrl: String,
) {
    GlideApp.with(context)
        .load(imageUrl)
        .centerInside()
        .into(this)
}
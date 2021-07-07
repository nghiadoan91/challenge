package com.example.opnchallenge

import android.app.Application
import android.content.Context

class TestApplication : Application() {
    companion object {
        @JvmStatic
        private val TAG = TestApplication::class.java.simpleName
    }

    override fun onCreate() {
        super.onCreate()
        setTheme(R.style.Theme_OpnChallenge)
    }

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
    }
}
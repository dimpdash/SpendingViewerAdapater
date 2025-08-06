package com.spendingviewer.adapter


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AdapterApplication : Application() {
    // You can override onCreate() or other Application methods if needed
    override fun onCreate() {
        super.onCreate()
        // Hilt setup is automatic, but you can do other app-wide initializations here
    }
}
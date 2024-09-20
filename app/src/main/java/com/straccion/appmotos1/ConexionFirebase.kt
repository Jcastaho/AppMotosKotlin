package com.straccion.appmotos1

import android.app.Application
import com.google.firebase.FirebaseApp

class ConexionFirebase : Application() {
    override fun onCreate() {
        super.onCreate()
        FirebaseApp.initializeApp(this)
    }
}

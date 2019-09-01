package vl.appa.app

import android.app.Application
import android.content.Context

class AppA: Application() {

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
    }

    companion object {

        lateinit var appContext: Context
    }
}
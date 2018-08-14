package com.fedesen.prueba

import android.app.Application
import android.content.Context

class App : Application() {

    companion object {

        private var mApp: App? = null

        val application: Application? get() = mApp

        fun getContext(): Context {
            if (mApp == null) {
                return Application()
            }

            return mApp!!.applicationContext
        }
    }

    override fun onCreate() {

        super.onCreate()

        mApp = this

    }
}

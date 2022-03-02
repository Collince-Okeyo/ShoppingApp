package com.ramgdeveloper

import android.app.Application
import com.ramgdeveloper.shoppingapp.BuildConfig
import timber.log.Timber

class ShoppingTimber: Application() {
    override fun onCreate() {
        super.onCreate()

        if(BuildConfig.DEBUG){
            Timber.plant(Timber.DebugTree())
        }
    }
}
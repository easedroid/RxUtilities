package com.reactivelocation

import android.app.Application
import com.rxlocations.RxHelper
import com.rxlocations.RxLocations

class RxApp: Application(){

    override fun onCreate() {
        super.onCreate()
        RxHelper.init(applicationContext).initializeTimber()
        RxLocations.init(applicationContext)
    }

}
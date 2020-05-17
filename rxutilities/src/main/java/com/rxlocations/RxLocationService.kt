package com.rxlocations

import android.app.Service
import android.content.Intent
import android.os.IBinder

class RxLocationService: Service(){



    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

}
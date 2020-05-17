package com.reactivelocation

import android.Manifest
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.rxlocations.PermissionCallback
import com.rxlocations.RxHelper
import com.rxlocations.RxLocations
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        val builder = RxLocations.Builder()
        val loc = builder.with(this)
                                .requestRuntimePermission(true)
                                .setFastestInterval(3000)
                                .setInterval(8000)
                                .build()

        /*loc.getLocationStream()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({location ->
                RxHelper.print("${location.latitude}  ${location.longitude}")


                loc.getAddressesFromLocation()
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(Schedulers.io())
                    .subscribe({addressList ->
                        for (index in 0..addressList.size){
                            RxHelper.print(addressList[index].locality)
                        }
                    }, {error ->

                    })

            }, {error ->

            })*/



        RxHelper.init(this).showToast("Showing message here")
        loc.getLocationStream()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({location ->
                RxHelper.print(location.toString())
            }, {error ->
                error.printStackTrace()
            })

        RxHelper.init(this).askPermission(Pair(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), object : PermissionCallback {
            override fun onGranted() {

            }

            override fun onDenied() {
                RxHelper.print("Permission Denied!!")
            }
        })


    }
}

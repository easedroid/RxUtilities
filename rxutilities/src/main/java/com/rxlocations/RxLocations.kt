package com.rxlocations

import android.Manifest
import android.app.Activity
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.*
import io.reactivex.Observable
import timber.log.Timber
import java.lang.ref.WeakReference
import java.util.*

class RxLocations{
    var contex: Context? = null

    var permissionRequired: Boolean? = null
    var interval: Int = 10000
    var fastestInterval: Int = 3000

    companion object{
        var weakRef: WeakReference<Context>? = null

        fun init(context: Context) = RxLocations().apply {
            //this.context = context
            weakRef = WeakReference(context)
            Timber.e("Setting Context!!")
        }
    }

    init {
        Timber.e("Initialize location engine!")
    }

    fun getCurrentLocation(): Observable<Location>{
        return Observable.create { emitter ->
            val locationRequest = LocationRequest()
            locationRequest.interval = interval.toLong()
            locationRequest.fastestInterval = fastestInterval.toLong()
            locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

            LocationServices.getFusedLocationProviderClient(weakRef?.get()!!)
                .requestLocationUpdates(locationRequest, object : LocationCallback(){
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        LocationServices.getFusedLocationProviderClient(weakRef!!.get()!!)
                            .removeLocationUpdates(this)
                        if (locationRequest!=null && locationResult?.locations?.size!!>0){
                            val lastIndex = locationResult.locations.size - 1
                            val latitude = locationResult.locations[lastIndex].latitude
                            val longitude = locationResult.locations[lastIndex].longitude
                            Timber.e("Location : $latitude. $longitude")
                            emitter.onNext(locationResult.locations[lastIndex])
                            emitter.onComplete()
                        }

                    }

                    override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                        super.onLocationAvailability(locationAvailability)
                    }
                }, Looper.getMainLooper())

            /*RxHelper.init(weakRef!!.get()!! as Activity).askPermission(Pair(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), object :PermissionCallback{
                override fun onGranted() {

                }

                override fun onDenied() {
                    emitter.onError(Throwable("Permission denied!!!"))
                }
            })*/
        }
    }


    fun getLocationStream(): Observable<Location>{
        return Observable.create { emitter ->
            val locationRequest = LocationRequest()
            locationRequest.interval = interval.toLong()
            locationRequest.fastestInterval = fastestInterval.toLong()
            locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

            LocationServices.getFusedLocationProviderClient(weakRef?.get()!!)
                .requestLocationUpdates(locationRequest, object : LocationCallback(){
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        /*LocationServices.getFusedLocationProviderClient(weakRef!!.get()!!)
                            .removeLocationUpdates(this)*/
                        if (locationRequest!=null && locationResult?.locations?.size!!>0){
                            val lastIndex = locationResult.locations.size - 1
                            val latitude = locationResult.locations[lastIndex].latitude
                            val longitude = locationResult.locations[lastIndex].longitude
                            //Timber.e("Location : $latitude. $longitude")
                            emitter.onNext(locationResult.locations[lastIndex])
                            //emitter.onComplete()
                        }

                    }

                    override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                        super.onLocationAvailability(locationAvailability)
                    }
                }, Looper.getMainLooper())
        }
    }


    fun getAddressesFromLocation(): Observable<List<Address>>{
        return Observable.create { emitter ->
            val locationRequest = LocationRequest()
            locationRequest.interval = interval.toLong()
            locationRequest.fastestInterval = fastestInterval.toLong()
            locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY

            LocationServices.getFusedLocationProviderClient(weakRef?.get()!!)
                .requestLocationUpdates(locationRequest, object : LocationCallback(){
                    override fun onLocationResult(locationResult: LocationResult?) {
                        super.onLocationResult(locationResult)
                        /*LocationServices.getFusedLocationProviderClient(weakRef!!.get()!!)
                            .removeLocationUpdates(this)*/
                        if (locationRequest!=null && locationResult?.locations?.size!!>0){
                            val lastIndex = locationResult.locations.size - 1
                            val latitude = locationResult.locations[lastIndex].latitude
                            val longitude = locationResult.locations[lastIndex].longitude
                            val geocoder = Geocoder(weakRef!!.get(), Locale.getDefault())
                            val addressList = geocoder.getFromLocation(latitude, longitude, 4)
                            //Timber.e("Location : $latitude. $longitude")
                            emitter.onNext(addressList)
                            emitter.onComplete()
                        }

                    }

                    override fun onLocationAvailability(locationAvailability: LocationAvailability?) {
                        super.onLocationAvailability(locationAvailability)
                    }
                }, Looper.getMainLooper())
        }
    }


    data class Builder(var contex: Context? = null){
        var flag: Boolean? = null
        var interval: Int? = null
        var fastestInterval: Int? = null


        fun with(contex: Context) = apply { this.contex = contex }
        fun requestRuntimePermission(flag: Boolean) = apply { this.flag = flag }
        fun setInterval(interval: Int) = apply {
            this@Builder.interval = interval
        }
        fun setFastestInterval(interval: Int) = apply {
            this@Builder.fastestInterval = interval
        }

        fun build() = RxLocations().apply {
            contex = this@Builder.contex
            permissionRequired = this@Builder.flag
            this@Builder.interval.let {
                if (it != null && it>0) {
                    interval = it
                }
            }
            this@Builder.fastestInterval.let {
                if (it != null && it>0) {
                    fastestInterval = it
                }
            }

        }
    }
}
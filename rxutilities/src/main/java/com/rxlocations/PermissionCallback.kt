package com.rxlocations

interface PermissionCallback {
    fun onGranted()
    fun onDenied()
}
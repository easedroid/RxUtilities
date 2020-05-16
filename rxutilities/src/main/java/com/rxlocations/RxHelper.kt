package com.rxlocations

import android.content.Context
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.livinglifetechway.quickpermissions_kotlin.runWithPermissions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsOptions
import com.livinglifetechway.quickpermissions_kotlin.util.QuickPermissionsRequest
import timber.log.Timber
import java.lang.ref.WeakReference

class RxHelper {

    var context: Context? = null
    var weakRef: WeakReference<Context>? = null

    private val quickPermissionsOption = QuickPermissionsOptions(
        rationaleMessage = "Custom rational message",
        permanentlyDeniedMessage = "Custom permanently denied message",
        rationaleMethod = { rationaleCallback(it) },
        permanentDeniedMethod = { permissionsPermanentlyDenied(it) },
        permissionsDeniedMethod = { whenPermAreDenied(it) }
    )

    var permissionCallback: PermissionCallback? = null

    companion object{
        fun init(context: Context) = RxHelper().apply {
            this.context = context
            this.weakRef = WeakReference(context)//context.applicationContext as WeakReference<Context>
        }

        fun print(msg: String){
            if (BuildConfig.DEBUG) {
                Timber.e(msg)
            }
        }

        /*fun toast(msg: String) = RxHelper().apply {
            showToast(msg)
        }*/
    }


    fun showToast(msg: String){
        if (BuildConfig.DEBUG){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }

    fun initializeTimber(){
        Timber.plant(Timber.DebugTree())
    }

    fun askPermission(pair: Pair<String, String>, callback: PermissionCallback) {
        permissionCallback = callback
        methodWithPermissions(pair)
    }

    fun methodWithPermissions(pair: Pair<String, String>) = weakRef!!.get().runWithPermissions(pair.first, pair.second, options = quickPermissionsOption){
        permissionCallback?.onGranted()
    }



    private fun rationaleCallback(req: QuickPermissionsRequest) {
        // this will be called when permission is denied once or more time. Handle it your way
        AlertDialog.Builder(weakRef!!.get()!!)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom rationale dialog. Please allow us to proceed " + "asking for permissions again, or cancel to end the permission flow.")
            .setPositiveButton("Go Ahead") { dialog, which ->
                req.proceed()
                permissionCallback?.onGranted()
            }
            .setNegativeButton("cancel") { dialog, which ->
                req.cancel()
                permissionCallback?.onDenied()
            }
            .setCancelable(false)
            .show()
    }

    private fun permissionsPermanentlyDenied(req: QuickPermissionsRequest) {
        // this will be called when some/all permissions required by the method are permanently
        // denied. Handle it your way.
        AlertDialog.Builder(weakRef!!.get()!!)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions permanently denied dialog. " +
                    "Please open app settings to open app settings for allowing permissions, " +
                    "or cancel to end the permission flow.")
            .setPositiveButton("App Settings") { dialog, which -> req.openAppSettings() }
            .setNegativeButton("Cancel") { dialog, which ->
                req.cancel()
                permissionCallback?.onDenied()
            }
            .setCancelable(false)
            .show()
    }

    private fun whenPermAreDenied(req: QuickPermissionsRequest) {
        // handle something when permissions are not granted and the request method cannot be called
        AlertDialog.Builder(weakRef!!.get()!!)
            .setTitle("Permissions Denied")
            .setMessage("This is the custom permissions denied dialog. \n${req.deniedPermissions.size}/${req.permissions.size} permissions denied")
            .setPositiveButton("OKAY") { _, _ -> permissionCallback?.onDenied() }
            .setCancelable(false)
            .show()
//        val toast = Toast.makeText(this, req.deniedPermissions.size.toString() + " permission(s) denied. This feature will not work.", Toast.LENGTH_LONG)
//        toast.setGravity(Gravity.CENTER, 0, 0)
//        toast.show()
    }
}
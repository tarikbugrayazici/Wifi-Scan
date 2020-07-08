package com.example.wifi_scan

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class PermissionHelper(private val activity: Activity) {

    private val REQUEST_CODE = 101

    fun checkPermission(block: () -> Unit) {
        val permission = ContextCompat.checkSelfPermission(
            activity,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

        if (permission != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(activity, R.string.permission_denied, Toast.LENGTH_SHORT).show()
            makeRequest()
        } else {
            Toast.makeText(activity, R.string.permission_granted, Toast.LENGTH_SHORT).show()
            block()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            REQUEST_CODE
        )
    }

}
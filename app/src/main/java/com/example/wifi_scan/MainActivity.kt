package com.example.wifi_scan

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.content.pm.PackageManager.PERMISSION_GRANTED
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    lateinit var wifiManager: WifiManager
    private var connections = ArrayList<Model>()
    private var wifiScanResults: ArrayList<ScanResult>? = null
    private val REQUEST_CODE = 101
    private val permissionHelper: PermissionHelper by lazy {
        PermissionHelper(
            this
        )
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setWifiManager()
        permissionHelper.checkPermission { checkWifiIsEnabled() }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
        when (requestCode) {
            REQUEST_CODE -> {

                if ((grantResults.isNotEmpty() && grantResults[0] == PERMISSION_GRANTED)) {
                    Toast.makeText(this, R.string.permission_granted, Toast.LENGTH_SHORT).show()
                    checkWifiIsEnabled()
                } else {
                    Toast.makeText(this, R.string.permission_denied, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun checkWifiIsEnabled() {
        if (!wifiManager.isWifiEnabled) {
            Toast.makeText(this, R.string.wifi_is_disabled, Toast.LENGTH_LONG)
                .show()
            wifiManager.isWifiEnabled = true
            scanWifi()
        }
    }

    private fun setWifiManager() {
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    }

    private fun setRecyclerView() {
        val linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager
        val adapter = ScannedWifiAdapter(this, connections)
        recyclerView.adapter = adapter
    }

    private fun scanWifi() {
        connections.clear()
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiManager.startScan()
        Toast.makeText(this, R.string.scanning_wifi, Toast.LENGTH_SHORT).show()

    }

    private fun handleWifiScanResults() {
        wifiScanResults?.let {
            for (result in it) {
                val connection =
                    Model(
                        bssid = result.BSSID,
                        ssid = result.SSID,
                        level = result.level
                    )
                connections.add(connection)
            }
        }
        setRecyclerView()
    }

    private val wifiReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            wifiScanResults = wifiManager.scanResults as ArrayList<ScanResult>?
            unregisterReceiver(this)
            handleWifiScanResults()
        }
    }
}

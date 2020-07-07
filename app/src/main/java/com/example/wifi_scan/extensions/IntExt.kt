package com.example.wifi_scan.extensions

fun Int.connectionLevel(connectionLevel: Int): String {
    var signalPower: String? = null
    if (connectionLevel!! <= 0 && connectionLevel >= -50) {
        signalPower = " Excellent "

    } else if (connectionLevel < -50 && connectionLevel >= -70) {
        signalPower = " Good "

    } else if (connectionLevel < -70 && connectionLevel >= -80) {
        signalPower = " Fair "

    } else if (connectionLevel < -80 && connectionLevel >= -100) {
        signalPower = " Weak "

    }
    return signalPower!!

}
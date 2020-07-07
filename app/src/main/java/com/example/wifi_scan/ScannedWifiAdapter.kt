package com.example.wifi_scan

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.wifi_scan.extensions.connectionLevel

class ScannedWifiAdapter(
    private val context: Context,
    private val list: ArrayList<Model>
) : RecyclerView.Adapter<ScannedWifiAdapter.MainActivityAdapterHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ScannedWifiAdapter.MainActivityAdapterHolder {
        return MainActivityAdapterHolder(
            LayoutInflater
                .from(context)
                .inflate(R.layout.recycler_view_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(
        holder: ScannedWifiAdapter.MainActivityAdapterHolder,
        position: Int
    ) {
        val connection = list[position]
        holder.connectionName.text = connection.ssid
        holder.connectionMacAddress.text = "MAC address: " + connection.bssid
        holder.connectionSignalStrength.text =
            "Signal Power:" + connection.level?.connectionLevel(connection.level!!) +
                    "(" + connection.level.toString() + " dBm)"
    }

    override fun getItemViewType(position: Int): Int {
        return position
    }

    inner class MainActivityAdapterHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var connectionName: TextView = itemView.findViewById<TextView>(R.id.connectionName)
        var connectionSignalStrength:TextView =
            itemView.findViewById<TextView>(R.id.connectionSignalStrength)
        var connectionMacAddress:TextView = itemView.findViewById<TextView>(R.id.connectedRouterMacAdress)


    }
}

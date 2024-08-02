package com.example.network_monitoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.networkmonitor.NetworkMonitor
import com.example.networkmonitor.NetworkStatusView

class NetworkMonitorWithUi : AppCompatActivity() {
  private lateinit var networkStatusView: NetworkStatusView
  private lateinit var  networkMonitor:NetworkMonitor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    networkStatusView = findViewById(R.id.network_status_view)

    networkStatusView.startMonitoring(this)


    // Network monitoring api.
     networkMonitor = NetworkMonitor(this)
    networkMonitor.networkStatus.observe(this) {
      if (it) {
        // Connection on
        Log.e("#", it.toString() + "  " + networkMonitor.isNetworkAvailable())
      } else {
        // Connection off
        Log.e("#", it.toString() + "  " + networkMonitor.isNetworkAvailable())
      }
    }

    // If you dont want to observe.
    val isNetworkAvailable=networkMonitor.isNetworkAvailable()

  }

  override fun onDestroy() {
    super.onDestroy()
    // Stop monitoring.
    networkStatusView.stopMonitoring()

    // Stop obeserving.
    networkMonitor.unregisterCallback()
  }
}

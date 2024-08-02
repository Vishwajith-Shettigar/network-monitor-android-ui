package com.example.network_monitoring

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.networkmonitor.NetworkMonitor

class NetworkMonitorWithoutUi  : AppCompatActivity() {
  private lateinit var  networkMonitor: NetworkMonitor

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)

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
    // Stop obeserving.
    networkMonitor.unregisterCallback()
  }
}

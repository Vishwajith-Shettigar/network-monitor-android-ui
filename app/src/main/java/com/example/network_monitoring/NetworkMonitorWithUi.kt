package com.example.network_monitoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.networkmonitor.NetworkStatusView

class NetworkMonitorWithUi : AppCompatActivity() {
  private lateinit var networkStatusView: NetworkStatusView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    networkStatusView = findViewById(R.id.network_status_view)
    networkStatusView.startMonitoring(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    // Stop monitoring.
    networkStatusView.stopMonitoring()
  }
}

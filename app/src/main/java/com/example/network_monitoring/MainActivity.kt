package com.example.network_monitoring

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.networkmonitor.NetworkStatusView

class MainActivity : AppCompatActivity() {
  private lateinit var networkStatusView: NetworkStatusView

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    networkStatusView = findViewById(R.id.network_status_view)
    networkStatusView.startMonitoring(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    networkStatusView.unregisterNetworkMonitor()
  }
}

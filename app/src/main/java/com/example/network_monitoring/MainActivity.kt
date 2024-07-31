package com.example.network_monitoring

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
  private lateinit var networkStatusView: NetworkStatusView

  @RequiresApi(Build.VERSION_CODES.Q)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.activity_main)
    networkStatusView = findViewById(R.id.network_status_view)
    networkStatusView.setFontFamily(R.font.airstrip)
    networkStatusView.startMonitoring(this)
  }

  override fun onDestroy() {
    super.onDestroy()
    networkStatusView.onDestroy()
  }
}
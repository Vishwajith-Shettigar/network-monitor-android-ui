package com.example.networkmonitor

import android.content.Context
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

class NetworkMonitor(context: Context) {

  private val connectivityManager =
    context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

  private val isNetworkAvailable = isNetworkAvailable()
  private val _networkStatus = MutableLiveData(isNetworkAvailable)
  val networkStatus: LiveData<Boolean> get() = _networkStatus

  var isFirstTime = true

  val networkCallback: NetworkCallback = object : NetworkCallback() {

    override fun onAvailable(network: Network) {
      super.onAvailable(network)
      if (!isFirstTime) {
        _networkStatus.postValue(true)
        isFirstTime = false
      }
    }

    override fun onLost(network: Network) {
      super.onLost(network)
      isFirstTime = false
      _networkStatus.postValue(false)
    }
  }

  init {
    if (!isNetworkAvailable)
      isFirstTime = false

    val networkRequest = NetworkRequest.Builder()
      .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
      .build()

    connectivityManager.registerNetworkCallback(
      networkRequest,
      networkCallback
    )
  }

  /**
   * Returns boolean indicating internet is on/off.
   *
   * @return True if the device has internet connectivity, false otherwise.
   */
  fun isNetworkAvailable(): Boolean {
    val activeNetwork = connectivityManager.activeNetwork
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
    return networkCapabilities != null && networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
  }

  /**
   * Unregisters the network callback to prevent memory leaks.
   */
  fun unregisterCallback() {
    connectivityManager.unregisterNetworkCallback(networkCallback)
  }
}

# Network Monitoring Library

A Kotlin-based Android library that provides a customizable `NetworkStatusView` to monitor network connectivity changes in real-time with smooth animations.

## Features

- Monitors network connectivity status.
- Provides customizable UI for connected and disconnected states.
- Smooth animations for showing and hiding the network status view.

## Screenshots

| Connected | Disconnected |
|-----------|--------------|
| <img src="https://github.com/user-attachments/assets/758a5cb8-90b8-4d9d-a939-a336b3c724f9" alt="network_on" width="400"/> | <img src="https://github.com/user-attachments/assets/e3f850d7-b12e-4e4b-a204-0fcd51af3d28" alt="no_network" width="400"/> |

## Installation

To use this library in your project, add the following dependency to your `build.gradle` file:

```gradle
dependencies {
     implementation 'com.github.Vishwajith-Shettigar:network-monitor-android-ui:1.0.2'
}
 ```


Add JitPack repository to your settings.gradle:

```
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```

# Usage

<br>

### 1. Network Monitoring with UI
### 2. Network Monitoring without UI

<br>

# 1. Network Monitoring with UI

Add the NetworkStatusView to your layout:

```
<com.example.networkmonitor.NetworkStatusView
    android:id="@+id/networkStatusView"
    android:layout_width="match_parent"
    android:layout_height="wrap_content"/>
```
In your MainActivity or any Activity/Fragment:

```
class MainActivity : AppCompatActivity() {

    private lateinit var networkStatusView: NetworkStatusView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        networkStatusView = findViewById(R.id.networkStatusView)
        networkStatusView.startMonitoring(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        networkStatusView.stopMonitoring()
    }
}

```

# Customization
You can customize various properties of the NetworkStatusView:

```
networkStatusView.apply {
    timeOut = 5000
    connectionOnText = "You are online"
    connectionOffText = "No internet connection"
    connectionOnbackgroundColor = Color.GREEN
    connectionOffbackgroundColor = Color.RED
    connectionOnTextColor = Color.WHITE
    connectionOffTextColor = Color.WHITE
    connectionOnDrawableResourceId = R.drawable.ic_network_on
    connectionOffDrawableResourceId = R.drawable.ic_network_off
    setFontFamily(R.font.custom_font)
}

// Returns True if the device has internet connectivity, false otherwise.
val networkStatus= networkStatusView.getNetworkStatus()

```

Exposed Methods and Properties <br>
`startMonitoring(lifecycleOwner: LifecycleOwner)`: Starts monitoring network connectivity. <br>
`stopMonitoring()`: Stops monitoring network connectivity.<br>
`setFontFamily(fontFamily: Int)`: Sets the font family for the status text.<br>
`timeOut: Long`: Duration in milliseconds for which the connected message is shown. Default is 3000ms.<br>
`connectionOnText: String`: Text to display when connected. Default is "Connected".<br>
`connectionOffText: String`: Text to display when disconnected. Default is "Disconnected".<br>
`connectionOnbackgroundColor: Int`: Background color when connected. Default is Color.CYAN.<br>
`connectionOffbackgroundColor: Int`: Background color when disconnected. Default is Color.GRAY.<br>
`connectionOnTextColor: Int`: Text color when connected. Default is Color.BLACK.<br>
`connectionOffTextColor: Int`: Text color when disconnected. Default is Color.BLACK.<br>
`connectionOnDrawableResourceId: Int`: Drawable resource ID for the icon when connected. Default is R.drawable.network_on.<br>
`connectionOffDrawableResourceId: Int`: Drawable resource ID for the icon when disconnected. Default is R.drawable.network_off.<br>
`getNetworkStatus()`: Returns True if the device has internet connectivity, false otherwise.


# 2. Network Monitoring without UI

## Example

```
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

```


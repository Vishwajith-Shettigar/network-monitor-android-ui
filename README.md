# Network Monitoring Library

A Kotlin-based Android library that provides a customizable `NetworkStatusView` to monitor network connectivity changes in real-time with smooth animations.

## Features

- Monitors network connectivity status.
- Provides customizable UI for connected and disconnected states.
- Smooth animations for showing and hiding the network status view.
- Easy to integrate into any Android project.

## Screenshots

| Connected | Disconnected |
|-----------|--------------|
| <img src="https://github.com/user-attachments/assets/758a5cb8-90b8-4d9d-a939-a336b3c724f9" alt="network_on" width="200"/> | <img src="https://github.com/user-attachments/assets/e3f850d7-b12e-4e4b-a204-0fcd51af3d28" alt="no_network" width="200"/> |

## Installation

To use this library in your project, add the following dependency to your `build.gradle` file:

```gradle
dependencies {
    implementation 'com.github.your-username:network-monitor:1.0.0'
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

## Usage

# Network Monitoring with UI

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
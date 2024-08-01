package com.example.networkmonitor

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.Transformation
import android.widget.LinearLayout
import androidx.core.content.res.ResourcesCompat
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.example.networkmonitor.databinding.ViewNetworkStatusBinding
import kotlinx.coroutines.*

class NetworkStatusView @JvmOverloads constructor(
  context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0,
) : LinearLayout(context, attrs, defStyleAttr) {

  private val networkMonitor = NetworkMonitor(context)
  private val binding: ViewNetworkStatusBinding

  // Flag needed to prevent online label from showing when user opens app for first time.
  var isFirstTime: Boolean = true

  var timeOut: Long = 3000

  var connectionOnText = "Connected..."
  var connectionOffText = "Disconnected..."

  var connectionOnbackgroundColor = Color.CYAN
  var connectionOffbackgroundColor = Color.GRAY

  var connectionOnTextColor = Color.BLACK
  var connectionOffTextColor = Color.BLACK

  var connectionOnDrawableResourceId = R.drawable.network_on
  var connectionOffDrawableResourceId = R.drawable.network_off

  init {
    binding = ViewNetworkStatusBinding.inflate(
      LayoutInflater.from(context),
      this, true
    )
  }

  /** Observe network status and update UI with animation. */
  fun startMonitoring(lifecycleOwner: LifecycleOwner) {
    networkMonitor.networkStatus.observe(lifecycleOwner, Observer { isConnected ->
      if (isConnected) {
        if (isFirstTime) {
          setHide(binding.networkStatusViewContainer)
          isFirstTime = false
          return@Observer
        }
        binding.networkStatusViewContainer.apply {
          setVisible(this)
          this.setBackgroundColor(connectionOnbackgroundColor)
        }

        binding.networkStatusText.apply {
          this.text = connectionOnText
          this.setTextColor(connectionOnTextColor)
        }

        binding.networkStatusIcon.setImageResource(connectionOnDrawableResourceId)
        CoroutineScope(Dispatchers.IO).launch()
        {
          delay(timeOut)
          withContext(Dispatchers.Main)
          {
            setHide(binding.networkStatusViewContainer)
          }
        }
      } else {
        isFirstTime = false
        binding.networkStatusViewContainer.apply {
          setVisible(this)
          this.setBackgroundColor(connectionOffbackgroundColor)
        }
        binding.networkStatusText.apply {
          this.text = connectionOffText
          this.setTextColor(connectionOffTextColor)
        }
        binding.networkStatusIcon.setImageResource(connectionOffDrawableResourceId)
      }
    })
  }

  /**
   * Smooth hide/visible animation.
   *
   * Animation code taken from SO thread
   * (https://stackoverflow.com/questions/23925907/slidedown-and-slideup-layout-with-animation).
   */
  fun setVisible(v: View) {
    v.measure(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT)
    val targetHeight = v.measuredHeight
    v.layoutParams.height = 1
    v.visibility = VISIBLE
    val a: Animation = object : Animation() {
      override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        v.layoutParams.height =
          if (interpolatedTime == 1f) WindowManager.LayoutParams.WRAP_CONTENT
          else (targetHeight * interpolatedTime).toInt()
        v.requestLayout()
      }

      override fun willChangeBounds(): Boolean {
        return true
      }
    }
    a.duration = (targetHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
  }

  fun setHide(v: View) {
    val initialHeight = v.measuredHeight
    val a: Animation = object : Animation() {
      override fun applyTransformation(interpolatedTime: Float, t: Transformation?) {
        if (interpolatedTime == 1f) {
          v.visibility = GONE
        } else {
          v.layoutParams.height = initialHeight - (initialHeight * interpolatedTime).toInt()
          v.requestLayout()
        }
      }

      override fun willChangeBounds(): Boolean {
        return true
      }
    }

    a.duration = (initialHeight / v.context.resources.displayMetrics.density).toInt().toLong()
    v.startAnimation(a)
  }

  fun setFontFamily(fontFamily: Int) {
    val typeface: Typeface? = ResourcesCompat.getFont(context, fontFamily)
    typeface.let {
      binding.networkStatusText.typeface = typeface
    }
  }

  fun unregisterNetworkMonitor() {
    networkMonitor.unregisterCallback()
  }
}

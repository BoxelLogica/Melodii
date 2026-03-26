package com.boxellogica.melodii

import android.annotation.SuppressLint
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsControllerCompat


class MainActivity : ComponentActivity() {
    private var secondScreenPresentation: SecondScreenPresentation? = null


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ensureOnMainDisplay()

        enableEdgeToEdge()
        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE

        setContentView(R.layout.functional_screen_layout)
    }

    //
    //  FIX GHOST REMAINING ON SECOND DISPLAY
    override fun onPause() {
        super.onPause()
        secondScreenPresentation?.dismiss()
        secondScreenPresentation = null
    }
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onResume() {
        super.onResume()

        setupPresentation()
    }


    @RequiresApi(Build.VERSION_CODES.R)
    private fun setupPresentation() {

        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val displays = displayManager.displays

        if (displays.size < 2) return

        val secondDisplay = displays.firstOrNull {it.displayId != Display.DEFAULT_DISPLAY} ?: return

        Log.d("DisplayCheck", "Main display: ${display?.displayId}")
        Log.d("DisplayCheck", "Presentation display: ${secondDisplay.displayId}")

        window.addFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)


        setContentView(R.layout.details_screen_layout)
        secondScreenPresentation = SecondScreenPresentation(this, secondDisplay)
        secondScreenPresentation?.show()
        secondScreenPresentation?.afterCreate()


//        detailsScreenPresentation?.updateInfo("Details")
    }



    @SuppressLint("UnsafeIntentLaunch")
    @RequiresApi(Build.VERSION_CODES.R)
    private fun ensureOnMainDisplay() {
        val currentDisplayId = display?.displayId ?: return

        if (currentDisplayId != Display.DEFAULT_DISPLAY) {

            val options = android.app.ActivityOptions.makeBasic().apply {
                launchDisplayId = Display.DEFAULT_DISPLAY
            }

            val intent = intent
            intent.addFlags(
                android.content.Intent.FLAG_ACTIVITY_NEW_TASK or
                        android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK
            )

            startActivity(intent, options.toBundle())
            finish()
        }
    }
}
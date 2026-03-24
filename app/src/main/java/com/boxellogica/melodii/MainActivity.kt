package com.boxellogica.melodii

import android.annotation.SuppressLint
import android.hardware.display.DisplayManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Display
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.annotation.RequiresApi
import androidx.core.view.WindowCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat


class MainActivity : ComponentActivity() {
    private var detailsScreenPresentation: DetailsScreenPresentation? = null


    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        ensureOnMainDisplay()

        val windowInsetsController =
            WindowCompat.getInsetsController(window, window.decorView)  // Gets View Controller for System
        windowInsetsController.systemBarsBehavior =
            WindowInsetsControllerCompat.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE  // Set bars to hide automatically
        windowInsetsController.hide(WindowInsetsCompat.Type.systemBars())   // Hide now

        setContentView(R.layout.functional_screen_layout)
    }



// TODO
//    FIX ISSUE WITH LAUNCHING ON BOTTOM SCREEN
//    ADD FUNCTIONALITY TO SWAP SCREEN



    //
    //  FIX GHOST REMAINING ON SECOND DISPLAY
    override fun onPause() {
        super.onPause()
        detailsScreenPresentation?.dismiss()
        detailsScreenPresentation = null
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

        detailsScreenPresentation = DetailsScreenPresentation(this, secondDisplay).apply {
            window?.setFlags(
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            )
            show()
        }

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
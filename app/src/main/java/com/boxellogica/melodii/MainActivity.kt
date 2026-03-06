package com.boxellogica.melodii

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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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



    //
    // SET-UP DISPLAY
    @RequiresApi(Build.VERSION_CODES.R)
    private fun setupPresentation() {
        val displayManager = getSystemService(DISPLAY_SERVICE) as DisplayManager
        val displayList = displayManager.displays

        if (displayList.size > 1) {
            var currentDisplayId = display?.displayId
            var secondDisplay: Display? = null

            if (currentDisplayId == 0) {
                secondDisplay = displayList[1]
            }
            else {
                secondDisplay = displayList[0]

            }
            setContentView(R.layout.functional_screen_layout)
            Log.d("DisplayCheck", "Activity is on Display: $currentDisplayId")
            Log.d("SecondaryDisplayCheck", "SecondaryDisplay is: $secondDisplay")

            detailsScreenPresentation = DetailsScreenPresentation(this, secondDisplay).apply {
                window?.setFlags(
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
                )
                show()
            }

            detailsScreenPresentation?.updateInfo("Details")
        }
    }
}
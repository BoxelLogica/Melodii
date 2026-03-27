package com.boxellogica.melodii

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar

class SecondScreenPresentation(
    context: Context,
    display: Display
) : Presentation(context, display) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.functional_screen_layout)



        val forwardsBtn = findViewById<Button>(R.id.forwardsBtn)
        val backwardsBtn = findViewById<Button>(R.id.backwardsBtn)
        val mainSeekBar = findViewById<SeekBar>(R.id.mainSeekBar)

        forwardsBtn.setOnClickListener {
            mainSeekBar.progress += 10
        }
        backwardsBtn.setOnClickListener {
            mainSeekBar.progress -= 10
        }
    }



//    fun updateInfo(text: String) {
//        findViewById<TextView>(R.id.detailsScreenText).text = text
//    }
}
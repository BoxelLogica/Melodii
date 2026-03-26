package com.boxellogica.melodii

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.widget.Button

class SecondScreenPresentation(
    context: Context,
    display: Display
) : Presentation(context, display) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.functional_screen_layout)


    }
    fun afterCreate() {
        val dockCenter = findViewById<Button>(R.id.dockCenter)
        dockCenter.requestFocus()
    }

//    fun updateInfo(text: String) {
//        findViewById<TextView>(R.id.detailsScreenText).text = text
//    }
}
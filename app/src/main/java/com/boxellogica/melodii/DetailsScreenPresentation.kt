package com.boxellogica.melodii

import android.app.Presentation
import android.content.Context
import android.os.Bundle
import android.view.Display
import android.widget.TextView

class DetailsScreenPresentation(
    context: Context,
    display: Display
) : Presentation(context, display) {

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.details_screen_layout)
    }


    fun updateInfo(text: String) {
        findViewById<TextView>(R.id.detailsScreenText).text = text
    }
}
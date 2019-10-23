package com.pastorm.tendelimonetimer

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class TimeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        doAsync {
            val textView = findViewById<TextView>(R.id.timer)
            val retriever = TimeRetrieverFactory().createRetriever(RetrieveMode.STATIC_WEBSITE)
            val res = retriever.retrieve()

            uiThread {
                textView.text = res?.toString() ?: "Unable to fetch the timer"
            }
        }
    }
}


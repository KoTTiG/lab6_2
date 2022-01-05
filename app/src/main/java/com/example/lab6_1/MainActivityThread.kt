package com.example.lab6_1

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import kotlin.math.roundToLong

class MainActivityThread : AppCompatActivity() {
    private var baseTime: Long = 0
    private var startTime: Long = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences
    private lateinit var backgroundThread: Thread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStart() {
        super.onStart()
        baseTime = sharedPref.getLong("seconds", 0)
        backgroundThread = getThread()
        backgroundThread.start()
        startTime = System.currentTimeMillis()
    }

    override fun onStop() {
        super.onStop()
        backgroundThread.interrupt()
        with(sharedPref.edit()) {
            putLong("seconds", getCurrentTimeToShow())
            apply()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getThread() = Thread {
        while (!Thread.currentThread().isInterrupted) {
            Thread.sleep(10)
            textSecondsElapsed.post {
                textSecondsElapsed.text = "Seconds elapsed: " + getCurrentTimeToShow()
            }
        }
    }

    private fun getCurrentTimeToShow(): Long {
        return baseTime + ((System.currentTimeMillis() - startTime) / 1000.0).roundToLong()
    }
}
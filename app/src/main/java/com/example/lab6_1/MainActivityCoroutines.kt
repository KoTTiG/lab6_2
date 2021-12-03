package com.example.lab6_1

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import kotlin.math.roundToLong

class MainActivityCoroutines : AppCompatActivity() {
    private var baseTime: Long = 0
    private var startTime: Long = 0
    private lateinit var textSecondsElapsed: TextView
    private lateinit var sharedPref: SharedPreferences
    private var executor: ExecutorService = Executors.newSingleThreadExecutor()

    companion object {
        const val SECONDS_KEY = "seconds"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPref = getPreferences(Context.MODE_PRIVATE)
        setContentView(R.layout.activity_main)
        textSecondsElapsed = findViewById(R.id.textSecondsElapsed)
    }

    override fun onStart() {
        super.onStart()
        baseTime = sharedPref.getLong(SECONDS_KEY, 0)
        executor.submit(getRunnable())
        startTime = System.currentTimeMillis()
    }

    override fun onStop() {
        super.onStop()
        with(sharedPref.edit()) {
            putLong(SECONDS_KEY, getCurrentTimeToShow())
            apply()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun getRunnable() = Runnable {
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
package com.example.lab6_2

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import java.util.concurrent.Executors
import android.graphics.BitmapFactory
import android.widget.ImageView
import java.net.URL


class MainActivity : AppCompatActivity() {
    private val executorService = Executors.newSingleThreadExecutor()
    private lateinit var imageView: ImageView
    private val imageUrl = URL("https://sun9-29.userapi.com/impg/ykoWHS26aTIBOcQPg5pPMFMNrW-lipy7POi_5g/ARBrfKLtvLE.jpg?size=750x930&quality=96&sign=9b3bda4acd79aae79aa60256535dbb06&type=album")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn).setOnClickListener(::getImage)
    }

    override fun onStart() {
        super.onStart()
        imageView = findViewById(R.id.image)
    }

    private fun getImage(view: View) {
        view.isClickable = false
        executorService.submit  {
            val image = BitmapFactory.decodeStream(imageUrl.openConnection().getInputStream())
            imageView.post {
                imageView.setImageBitmap(image)
            }
            view.isClickable = true

        }
    }
}
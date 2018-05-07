package com.example.artyomvlasov.trendrecommendator.app.main

import android.app.Activity
import android.content.Intent
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.photo.PhotoResultActivity
import kotlinx.android.synthetic.main.activity_main.*

const val REQUEST_IMAGE_CAPTURE = 1

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        startButton.setOnClickListener {
            dispatchTakePictureIntent()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            startPhotoResultActivity(data)
        }
    }

    private fun startPhotoResultActivity(data: Intent) {
        val intent = Intent(this, PhotoResultActivity::class.java)
        intent.putExtras(data.extras!!)
        startActivity(intent)
    }

    private fun dispatchTakePictureIntent() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

}

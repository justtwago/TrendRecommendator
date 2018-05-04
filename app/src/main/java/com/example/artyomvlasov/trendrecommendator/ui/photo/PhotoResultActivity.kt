package com.example.artyomvlasov.trendrecommendator.ui.photo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette

import com.example.artyomvlasov.trendrecommendator.ColorUtils
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.ui.clothes.ClothesActivity
import kotlinx.android.synthetic.main.activity_photo_result.*

class PhotoResultActivity : AppCompatActivity() {
    private val extras by lazy { intent.extras }
    private val imageBitmap by lazy { extras!!.get("data") as Bitmap }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_result)

        setPhoto(imageBitmap)
        setDominantColorView(imageBitmap)
        goButton.setOnClickListener {
            val intent = Intent(this, ClothesActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setPhoto(imageBitmap: Bitmap) {
        photo.setImageBitmap(imageBitmap)
    }

    private fun setDominantColorView(imageBitmap: Bitmap) {
        Palette.from(imageBitmap)
                .generate { palette ->
                    val textSwatch = palette.vibrantSwatch
                    val dominantColor: Int
                    dominantColor = textSwatch?.rgb ?: palette.getDominantColor(Color.BLACK)
                    colorView.setBackgroundColor(dominantColor)
                    colorText.text = ColorUtils().getColorName(dominantColor)
                }
    }
}

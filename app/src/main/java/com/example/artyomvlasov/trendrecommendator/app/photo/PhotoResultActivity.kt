package com.example.artyomvlasov.trendrecommendator.app.photo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette

import com.example.artyomvlasov.trendrecommendator.util.ColorUtils
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.clothes.ClothesActivity
import com.example.artyomvlasov.trendrecommendator.app.utils.Constatns.CATEGORY_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constatns.COLOR_KEY
import kotlinx.android.synthetic.main.activity_photo_result.*

class PhotoResultActivity : AppCompatActivity() {
    private val extras by lazy { intent.extras }
    private val imageBitmap by lazy { extras!!.get("data") as Bitmap }
    private lateinit var color: String
    private var category: String = "shirt"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_result)

        setPhoto(imageBitmap)
        setDominantColorView(imageBitmap)
        goButton.setOnClickListener {
            val intent = Intent(this, ClothesActivity::class.java)
            intent.putExtra(COLOR_KEY, color)
            intent.putExtra(CATEGORY_KEY, category)
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
                    color = ColorUtils().getColorName(dominantColor)
                    colorText.text = color
                }
    }
}

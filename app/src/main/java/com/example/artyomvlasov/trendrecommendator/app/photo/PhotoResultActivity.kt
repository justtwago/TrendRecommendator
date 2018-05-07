package com.example.artyomvlasov.trendrecommendator.app.photo

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette

import com.example.artyomvlasov.trendrecommendator.util.ColorUtils
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.clothes.main.ClothesActivity
import com.example.artyomvlasov.trendrecommendator.app.utils.Category
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.CATEGORY_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.COLOR_KEY
import kotlinx.android.synthetic.main.activity_photo_result.*

class PhotoResultActivity : AppCompatActivity() {
    private val extras by lazy { intent.extras }
    private val imageBitmap by lazy { extras!!.get("data") as Bitmap }
    private lateinit var color: String
    private var category: String = "shirt"
    private var selectedCategory: Category = Category.TROUSER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_result)

        recognizeColor(imageBitmap)
        setClickListeners()
    }

    private fun setClickListeners() {
        goButton.setOnClickListener {
            val intent = Intent(this, ClothesActivity::class.java)
            intent.putExtra(COLOR_KEY, color)
            intent.putExtra(CATEGORY_KEY, selectedCategory.name)
            startActivity(intent)
        }

        shirtIcon.setOnClickListener {
            shirtIcon.setIconEnabled(true, true)
            trousersIcon.setIconEnabled(false, true)
            shoesIcon.setIconEnabled(false, true)
            selectedCategory = Category.SHIRT
        }

        trousersIcon.setOnClickListener {
            shirtIcon.setIconEnabled(false, true)
            trousersIcon.setIconEnabled(true, true)
            shoesIcon.setIconEnabled(false, true)
            selectedCategory = Category.TROUSER
        }

        shoesIcon.setOnClickListener {
            shirtIcon.setIconEnabled(false, true)
            trousersIcon.setIconEnabled(false, true)
            shoesIcon.setIconEnabled(true, true)
            selectedCategory = Category.SNEAKER
        }
    }

    private fun recognizeColor(imageBitmap: Bitmap) {
        Palette.from(imageBitmap)
                .generate { palette ->
                    val textSwatch = palette.vibrantSwatch
                    val dominantColor: Int
                    dominantColor = textSwatch?.rgb ?: palette.getDominantColor(Color.BLACK)
                    color = ColorUtils().getColorName(dominantColor)
                    val text = resources.getString(R.string.recognized_wear_info, color.toLowerCase(), category.toLowerCase())
                    infoText.text = text
                }
    }
}

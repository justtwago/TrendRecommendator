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
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.CATEGORY_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.COLOR_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.GENDER_KEY
import com.example.artyomvlasov.trendrecommendator.tensorflow.ImageClassifier
import com.example.artyomvlasov.trendrecommendator.util.ClothesTypeManager
import com.example.artyomvlasov.trendrecommendator.util.choiceHelper.ApiType
import com.example.artyomvlasov.trendrecommendator.util.choiceHelper.ClothItem
import com.example.artyomvlasov.trendrecommendator.util.choiceHelper.HelperFactory
import com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColor
import com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColors
import kotlinx.android.synthetic.main.activity_photo_result.*

class PhotoResultActivity : AppCompatActivity() {
    private val extras by lazy { intent.extras }
    private val imageBitmap by lazy { extras!!.get("data") as Bitmap }
    private val classifier by lazy { ImageClassifier(this) }
    private var color = ""
    private var category = ""
    private var gender = Constants.MEN
    private var selectedCategory: Category = Category.TROUSER

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_result)

        recognizeClothes(imageBitmap)
        setClickListeners()
    }

    private fun setClickListeners() {
        goButton.setOnClickListener {
            val intent = Intent(this, ClothesActivity::class.java)
            intent.putExtra(COLOR_KEY, color)
            intent.putExtra(CATEGORY_KEY, selectedCategory.name)
            intent.putExtra(GENDER_KEY, gender)
            startActivity(intent)
        }

        maleIcon.setOnClickListener {
            maleIcon.setIconEnabled(true, true)
            femaleIcon.setIconEnabled(false, true)
            gender = Constants.MEN
        }

        femaleIcon.setOnClickListener {
            femaleIcon.setIconEnabled(true, true)
            maleIcon.setIconEnabled(false, true)
            gender = Constants.WOMEN
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

    private fun recognizeClothes(imageBitmap: Bitmap) {
        saveClothesColor(imageBitmap)
        saveClothesType(imageBitmap)
        printClothesColorAndType()
    }

    private fun printClothesColorAndType() {
        val text = resources.getString(R.string.recognized_wear_info,
                color.toLowerCase(),
                category.toLowerCase())
        infoText.text = text
    }

    private fun saveClothesColor(imageBitmap: Bitmap) {
        val palette = Palette.from(imageBitmap).generate()
        saveDominantColor(palette)
    }

    private fun saveClothesType(imageBitmap: Bitmap) {
        category = ClothesTypeManager.getClothesName(classifier.classifyFrame(imageBitmap))
    }

    private fun suggested(): ClothItem? {
        val apiType = ApiType.valueOf(category)
        val clothItem = ClothItem(ApiColor.valueOf(color), apiType)
        val suggest = HelperFactory.fromFile("rules.txt")
                .suggest(clothItem, apiType)
        return suggest
    }

    private fun saveDominantColor(palette: Palette) {
        val textSwatch = palette.vibrantSwatch
        val dominantColor: Int
        dominantColor = textSwatch?.rgb ?: palette.getDominantColor(Color.BLACK)
        color = ApiColors.fromRGB(Color.valueOf(dominantColor)).name
    }

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }
}

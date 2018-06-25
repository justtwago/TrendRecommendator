package com.example.artyomvlasov.trendrecommendator.app.photo

import android.annotation.TargetApi
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Color
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.graphics.Palette
import android.view.View

import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.clothes.main.ClothesActivity
import com.example.artyomvlasov.trendrecommendator.app.utils.Category
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.TYPE_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.COLOR_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.GENDER_KEY
import com.example.artyomvlasov.trendrecommendator.tensorflow.ImageClassifier
import com.example.artyomvlasov.trendrecommendator.util.choiceHelper.*
import com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColor
import com.example.artyomvlasov.trendrecommendator.util.colorClassification.ApiColors
import kotlinx.android.synthetic.main.activity_photo_result.*

private const val RULES_PATH = "/Users/artyomvlasov/AndroidStudioProjects/TrendRecommendator/app/src/main/assets/rules.txt"

class PhotoResultActivity : AppCompatActivity() {
    private val extras by lazy { intent.extras }
    private val imageBitmap by lazy { extras!!.get("data") as Bitmap }
    private val classifier by lazy { ImageClassifier(this) }
    private var clothesColor = ApiColor.Black
    private var clothesType = ApiType.UNKNOWN
    private var clothesCategory = ApiCategory.TOP
    private var gender = Constants.MEN

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photo_result)

        recognizeClothes(imageBitmap)
        setButtonsVisibility()
        setClickListeners()
    }

    private fun setButtonsVisibility() {
        when (ApiCategory.getCategoryByType[clothesType]) {
            ApiCategory.TOP -> shirtIcon.visibility = View.GONE
            ApiCategory.BOTTOM -> trousersIcon.visibility = View.GONE
            ApiCategory.SHOES -> shoesIcon.visibility = View.GONE
        }
    }

    private fun setClickListeners() {
        goButton.setOnClickListener {
            val suggestedItem: ClothItem = HelperFactory.fromFile(RULES_PATH).suggest(ClothItem(clothesColor, clothesType), clothesCategory)
            val intent = Intent(this, ClothesActivity::class.java)
            intent.putExtra(COLOR_KEY, suggestedItem.color().name)
            intent.putExtra(TYPE_KEY, suggestedItem.type().name)
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
            clothesCategory = ApiCategory.TOP
        }

        trousersIcon.setOnClickListener {
            shirtIcon.setIconEnabled(false, true)
            trousersIcon.setIconEnabled(true, true)
            shoesIcon.setIconEnabled(false, true)
            clothesCategory = ApiCategory.BOTTOM
        }

        shoesIcon.setOnClickListener {
            shirtIcon.setIconEnabled(false, true)
            trousersIcon.setIconEnabled(false, true)
            shoesIcon.setIconEnabled(true, true)
            clothesCategory = ApiCategory.SHOES
        }
    }

    private fun recognizeClothes(imageBitmap: Bitmap) {
        saveClothesColor(imageBitmap)
        saveClothesType(imageBitmap)
        printClothesColorAndType()
    }

    private fun printClothesColorAndType() {
        val text = resources.getString(R.string.recognized_wear_info,
                clothesColor.toString().toLowerCase(),
                clothesType.toString().toLowerCase())
        infoText.text = text
    }

    private fun saveClothesColor(imageBitmap: Bitmap) {
        val palette = Palette.from(imageBitmap).generate()
        saveDominantColor(palette)
    }

    private fun saveClothesType(imageBitmap: Bitmap) {
        clothesType = ApiType.valueOf(classifier.classifyFrame(imageBitmap))
    }

    @TargetApi(Build.VERSION_CODES.O)
    private fun saveDominantColor(palette: Palette) {
        val textSwatch = palette.vibrantSwatch
        val dominantColor: Int
        dominantColor = textSwatch?.rgb ?: palette.getDominantColor(Color.BLACK)
        clothesColor = ApiColors.fromRGB(Color.valueOf(dominantColor))
    }

    override fun onDestroy() {
        super.onDestroy()
        classifier.close()
    }
}

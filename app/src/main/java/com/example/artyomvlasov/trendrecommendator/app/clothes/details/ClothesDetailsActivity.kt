package com.example.artyomvlasov.trendrecommendator.app.clothes.details

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants
import com.example.artyomvlasov.trendrecommendator.app.utils.extensions.consume
import com.example.artyomvlasov.trendrecommendator.app.utils.extensions.setImageFromUrl
import com.example.artyomvlasov.trendrecommendator.data.ClothesItem
import kotlinx.android.synthetic.main.activity_clothes_details.*

class ClothesDetailsActivity : AppCompatActivity() {
    private val clothesItem by lazy { intent.getParcelableExtra(Constants.CLOTHES_ITEM_KEY) as ClothesItem }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes_details)
        setupToolbar()
        inflateDetails()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun inflateDetails() {
        clothesIcon.setImageFromUrl(clothesItem.image.sizes.original.url)
        clothesName.text = clothesItem.name
        clothesDescription.text = clothesItem.description
        goToSiteButton.setOnClickListener {
            openClothesMarketWebsite()
        }
    }

    private fun openClothesMarketWebsite() {
        val intent = Intent(Intent.ACTION_VIEW)
        intent.data = Uri.parse(clothesItem.clickUrl)
        startActivity(intent)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> consume(::onBackPressed)
                else -> super.onOptionsItemSelected(item)
            }
}

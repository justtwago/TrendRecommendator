package com.example.artyomvlasov.trendrecommendator.app.clothes.main

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.utils.extensions.consume
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.TYPE_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.COLOR_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants.GENDER_KEY
import com.example.artyomvlasov.trendrecommendator.data.ClothesItem
import dagger.android.AndroidInjection
import kotlinx.android.synthetic.main.activity_clothes.*
import javax.inject.Inject

class ClothesActivity : AppCompatActivity() {
    private val clothesAdapter by lazy { ClothesAdapter(this, emptyList()) }
    private val color by lazy { intent.getStringExtra(COLOR_KEY) }
    private val category by lazy { intent.getStringExtra(TYPE_KEY) }
    private val gender by lazy { intent.getStringExtra(GENDER_KEY) }

    @Inject
    lateinit var presenter: ClothesInterface

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes)

        initPresenter()
        setupToolbar()
        setupRecyclerView()
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun initPresenter() {
        presenter.init(color, category, gender)
    }

    private fun setupRecyclerView() {
        with(clothesRecyclerView) {
            adapter = clothesAdapter
            layoutManager = LinearLayoutManager(this@ClothesActivity)
            addItemDecoration(DividerItemDecoration(this@ClothesActivity, DividerItemDecoration.VERTICAL))
            presenter.onRecyclerViewReady(::updateClothesAdapter)
        }
    }

    private fun updateClothesAdapter(items: List<ClothesItem>) {
        clothesAdapter.setData(items)
        clothesAdapter.notifyDataSetChanged()
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> consume(::onBackPressed)
                else -> super.onOptionsItemSelected(item)
            }
}

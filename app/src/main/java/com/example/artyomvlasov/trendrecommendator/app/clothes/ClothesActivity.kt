package com.example.artyomvlasov.trendrecommendator.app.clothes

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.MenuItem
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.main.MainActivity
import com.example.artyomvlasov.trendrecommendator.app.utils.Constatns.CATEGORY_KEY
import com.example.artyomvlasov.trendrecommendator.app.utils.Constatns.COLOR_KEY
import com.example.artyomvlasov.trendrecommendator.data.ClothesItem
import com.example.artyomvlasov.trendrecommendator.repositories.ClothesRepository
import com.example.artyomvlasov.trendrecommendator.util.LazyLoadingScrollListener
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_clothes.*
import javax.inject.Inject

class ClothesActivity : AppCompatActivity() {
    private val clothesAdapter by lazy { ClothesAdapter(emptyList()) }
    private val color by lazy { intent.getStringExtra(COLOR_KEY) }
    private val category by lazy { intent.getStringExtra(CATEGORY_KEY) }

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
        presenter.init(color, category)
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
                android.R.id.home -> {
                    onBackPressed()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }
}

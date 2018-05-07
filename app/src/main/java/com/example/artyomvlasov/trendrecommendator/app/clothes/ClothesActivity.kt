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
    private var disposable: Disposable? = null
    private val clothesAdapter by lazy { ClothesAdapter(emptyList()) }
    private val color by lazy { intent.getStringExtra(COLOR_KEY) }
    private val category by lazy { intent.getStringExtra(CATEGORY_KEY) }

    @Inject
    lateinit var clothesRepository: ClothesRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes)

        setupToolbar()
        setupRecyclerView()
        inflateClothes()
    }

    private fun openMainActivity(): Boolean {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        return true
    }

    private fun setupToolbar() {
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun inflateClothes() {
        disposable = clothesRepository.getClothes(color, category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            clothesAdapter.setData(it.products)
                            clothesAdapter.notifyDataSetChanged()
                        }
                )
    }

    private fun getNextPage(offset: Int) {
        disposable = clothesRepository.getClothes(color, category, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            clothesAdapter.setData(it.products)
                            clothesAdapter.notifyDataSetChanged()
                        }
                )
    }

    private fun setupRecyclerView() {
        with(clothesRecyclerView) {
            adapter = clothesAdapter
            layoutManager = LinearLayoutManager(this@ClothesActivity)
            addItemDecoration(DividerItemDecoration(this@ClothesActivity, DividerItemDecoration.VERTICAL))
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}

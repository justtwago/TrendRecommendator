package com.example.artyomvlasov.trendrecommendator.ui.clothes

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.repositories.ClothesRepository
import dagger.android.AndroidInjection
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_clothes.*
import javax.inject.Inject

class ClothesActivity : AppCompatActivity() {
    @Inject
    lateinit var clothesRepository: ClothesRepository

    private var disposable: Disposable? = null
    private val clothesAdapter by lazy { ClothesAdapter(emptyList()) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_clothes)

        setupRecyclerView()
        inflateClothes()
    }

    private fun inflateClothes() {
        disposable = clothesRepository.getClothes("red", "dress")
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
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable?.dispose()
    }
}

package com.example.artyomvlasov.trendrecommendator.app.clothes

import android.arch.lifecycle.ViewModel
import com.example.artyomvlasov.trendrecommendator.data.ClothesItem
import com.example.artyomvlasov.trendrecommendator.repositories.ClothesRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class ClothesPresenter @Inject constructor(private val clothesRepository: ClothesRepository) : ClothesInterface, ViewModel() {
    private var color: String? = null
    private var category: String? = null
    private var disposable: Disposable? = null

    override fun init(color: String?, category: String?) {
        this.color = color
        this.category = category
    }

    override fun onRecyclerViewReady(action: (List<ClothesItem>) -> Unit) {
        disposable = clothesRepository.getClothes(color, category)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            action(it.products)
                        }
                )
    }

    private fun getNextPage(offset: Int, action: (List<ClothesItem>) -> Unit) {
        disposable = clothesRepository.getClothes(color, category, offset)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribeBy(
                        onSuccess = {
                            action(it.products)
                        }
                )
    }

    override fun onCleared() {
        super.onCleared()
        disposable?.dispose()
    }

}
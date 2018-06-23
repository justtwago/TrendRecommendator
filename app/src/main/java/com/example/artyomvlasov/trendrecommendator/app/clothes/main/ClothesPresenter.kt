package com.example.artyomvlasov.trendrecommendator.app.clothes.main

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
    private var gender: String? = null
    private var disposable: Disposable? = null

    override fun init(color: String?, category: String?, gender: String) {
        this.color = color
        this.category = category
        this.gender = gender
    }

    override fun onRecyclerViewReady(action: (List<ClothesItem>) -> Unit) {
        disposable = clothesRepository.getClothes(color, category, gender)
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
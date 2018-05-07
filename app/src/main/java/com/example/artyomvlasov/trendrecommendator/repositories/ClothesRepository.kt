package com.example.artyomvlasov.trendrecommendator.repositories

import com.example.artyomvlasov.trendrecommendator.data.Clothes
import io.reactivex.Single


interface ClothesRepository {

    fun getClothes(color: String?, category: String?, offset: Int = 0, limit: Int = 10): Single<Clothes>
}
package com.example.artyomvlasov.trendrecommendator.repositories

import com.example.artyomvlasov.trendrecommendator.data.Clothes
import com.example.artyomvlasov.trendrecommendator.services.ClothesService
import io.reactivex.Single
import java.lang.StringBuilder


class ClothesRepositoryImpl(private val clothesService: ClothesService) : ClothesRepository {
    override fun getClothes(color: String, category: String, offset: Int, limit: Int): Single<Clothes> {
        return clothesService.getClothes(
                fits = StringBuilder()
                        .append(color)
                        .append("+")
                        .append(category)
                        .toString(),
                offset = offset,
                limit = limit)
    }
}
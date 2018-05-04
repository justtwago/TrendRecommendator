package com.example.artyomvlasov.trendrecommendator.repositories

import com.example.artyomvlasov.trendrecommendator.data.Clothes
import com.example.artyomvlasov.trendrecommendator.services.ClothesService
import io.reactivex.Single


class ClothesRepositoryImpl(private val clothesService: ClothesService) : ClothesRepository {
    override fun getClothes(color: String, type: String, offset: Int, limit: Int): Single<Clothes> {
        return clothesService.getClothes(
                fits = color + "+" + type,
                offset = offset,
                limit = limit)
    }
}
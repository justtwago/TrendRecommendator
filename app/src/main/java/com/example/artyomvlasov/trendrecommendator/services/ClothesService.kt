package com.example.artyomvlasov.trendrecommendator.services

import com.example.artyomvlasov.trendrecommendator.data.Clothes
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

const val API_KEY = "uid7396-40817414-61"

interface ClothesService {
    @GET("products")
    fun getClothes(
            @Query("pid") apiKey: String = API_KEY,
            @Query("fts") fits: String,
            @Query("offset") offset: Int,
            @Query("limit") limit: Int
    ): Single<Clothes>
}
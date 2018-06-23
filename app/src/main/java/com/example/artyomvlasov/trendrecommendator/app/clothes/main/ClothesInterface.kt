package com.example.artyomvlasov.trendrecommendator.app.clothes.main

import com.example.artyomvlasov.trendrecommendator.data.ClothesItem

interface ClothesInterface {
    fun onRecyclerViewReady(action: (List<ClothesItem>) -> Unit)
    fun init(color: String?, category: String?, gender: String)
}
package com.example.artyomvlasov.trendrecommendator.data

data class ClothesItem(
        val id: Long,
        val image: Image,
        val name: String,
        val description: String,
        val clickUrl: String
)

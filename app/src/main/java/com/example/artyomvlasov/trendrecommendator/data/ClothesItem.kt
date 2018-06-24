package com.example.artyomvlasov.trendrecommendator.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ClothesItem(
        val id: Long,
        val image: Image,
        val name: String,
        val description: String,
        val clickUrl: String
) : Parcelable

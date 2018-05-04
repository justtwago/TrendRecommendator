package com.example.artyomvlasov.trendrecommendator.data

import com.google.gson.annotations.SerializedName


data class Image(
        @SerializedName("IPhoneSmall")
        val thumb: ImageSize,
        @SerializedName("Original")
        val original: ImageSize
)
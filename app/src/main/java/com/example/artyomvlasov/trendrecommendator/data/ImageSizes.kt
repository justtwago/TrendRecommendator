package com.example.artyomvlasov.trendrecommendator.data

import com.google.gson.annotations.SerializedName


data class ImageSizes(
        @SerializedName("IPhoneSmall")
        val thumb: Size,
        @SerializedName("Original")
        val original: Size?
)
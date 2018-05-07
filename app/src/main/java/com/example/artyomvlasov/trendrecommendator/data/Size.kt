package com.example.artyomvlasov.trendrecommendator.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class Size(
        val sizeName: String,
        val url: String
) : Parcelable
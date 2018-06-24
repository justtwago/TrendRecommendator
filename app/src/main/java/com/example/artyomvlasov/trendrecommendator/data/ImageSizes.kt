package com.example.artyomvlasov.trendrecommendator.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class ImageSizes(
        @SerializedName("IPhoneSmall")
        val thumb: Size,
        @SerializedName("Original")
        val original: Size
): Parcelable
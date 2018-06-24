@file:JvmName("ImageViewExtensions")

package com.example.artyomvlasov.trendrecommendator.app.utils.extensions

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.artyomvlasov.trendrecommendator.R

fun ImageView.setImageFromUrl(url: String) {
    Glide.with(this)
            .load(url)
            .apply(RequestOptions
                    .placeholderOf(R.drawable.bowtie))
            .into(this)
}


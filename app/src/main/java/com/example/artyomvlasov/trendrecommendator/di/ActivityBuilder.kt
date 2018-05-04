package com.example.artyomvlasov.trendrecommendator.di

import com.example.artyomvlasov.trendrecommendator.ui.clothes.ClothesActivity
import com.example.artyomvlasov.trendrecommendator.ui.main.MainActivity
import com.example.artyomvlasov.trendrecommendator.ui.photo.PhotoResultActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector


@Module
abstract class ActivityBuilder {
    @ContributesAndroidInjector
    abstract fun bindMainActivity(): MainActivity

    @ContributesAndroidInjector
    abstract fun bindClothesActivity(): ClothesActivity

    @ContributesAndroidInjector
    abstract fun bindPhotoResultActivity(): PhotoResultActivity
}
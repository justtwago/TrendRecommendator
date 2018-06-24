package com.example.artyomvlasov.trendrecommendator.di

import com.example.artyomvlasov.trendrecommendator.app.clothes.main.ClothesActivity
import com.example.artyomvlasov.trendrecommendator.app.main.MainActivity
import com.example.artyomvlasov.trendrecommendator.app.photo.PhotoResultActivity
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
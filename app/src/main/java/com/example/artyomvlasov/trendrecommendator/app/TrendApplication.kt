package com.example.artyomvlasov.trendrecommendator.app

import android.app.Activity
import android.app.Application
import com.example.artyomvlasov.trendrecommendator.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject


class TrendApplication : Application(), HasActivityInjector {

    override fun activityInjector() = activityInjector

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        setupDagger()
    }

    private fun setupDagger() {
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)
    }
}
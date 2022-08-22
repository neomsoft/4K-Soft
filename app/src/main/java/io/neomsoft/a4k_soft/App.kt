package io.neomsoft.a4k_soft

import android.app.Application
import io.neomsoft.a4k_soft.di.DI

class App: Application() {

    override fun onCreate() {
        super.onCreate()

        DI.initialize(this)
    }
}
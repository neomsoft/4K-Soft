package io.neomsoft.a4k_soft.di

import android.app.Application
import io.neomsoft.a4k_soft.data.room.AppDatabase
import io.neomsoft.a4k_soft.di.modules.*
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

object DI {

    fun initialize(application: Application) {
        startKoin {
            androidLogger()
            androidContext(application)

            modules(
                ApiModule.module,
                RepositoryModule.module,
                ViewModelsModule.module,
                ContextModule(application).module,
                DatabaseModule(AppDatabase.getInstance(application)).module
            )
        }
    }
}
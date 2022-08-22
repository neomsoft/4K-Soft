package io.neomsoft.a4k_soft.di.modules

import io.neomsoft.a4k_soft.data.room.AppDatabase
import org.koin.dsl.module

class DatabaseModule(private val database: AppDatabase) {

    val module = module {
        single { database }
    }
}
package io.neomsoft.a4k_soft.di.modules

import android.content.Context
import org.koin.dsl.module


class ContextModule(private val context: Context) {

    val module = module {
        single { context }
    }
}
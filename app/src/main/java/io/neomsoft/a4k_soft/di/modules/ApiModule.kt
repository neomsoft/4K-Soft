package io.neomsoft.a4k_soft.di.modules

import io.neomsoft.a4k_soft.data.net.notes.NotesApi
import io.neomsoft.a4k_soft.data.net.notes.NotesApiImpl
import org.koin.dsl.module

object ApiModule {

    val module = module {
        single<NotesApi> { NotesApiImpl(get()) }
    }
}
package io.neomsoft.a4k_soft.di.modules

import io.neomsoft.a4k_soft.data.repository.DataRepository
import io.neomsoft.a4k_soft.data.repository.IDataRepository
import org.koin.dsl.module

object RepositoryModule {

    val module = module {
        single<IDataRepository> { DataRepository(
            get(),
            get(),
            get()
        ) }
    }
}
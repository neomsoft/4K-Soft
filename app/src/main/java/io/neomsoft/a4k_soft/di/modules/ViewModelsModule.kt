package io.neomsoft.a4k_soft.di.modules

import io.neomsoft.a4k_soft.presentation.edit.EditNoteViewModel
import io.neomsoft.a4k_soft.presentation.notes.NotesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ViewModelsModule {

    val module = module {
        viewModel { NotesViewModel(
            get(),
        ) }
        viewModel { EditNoteViewModel(
            it.get(),
            get(),
        ) }
    }
}
package io.neomsoft.a4k_soft.presentation.edit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.neomsoft.a4k_soft.data.entities.Note
import io.neomsoft.a4k_soft.data.repository.IDataRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class EditNoteViewModel(
    note: Note,
    private val repository: IDataRepository,
) : ViewModel() {

    var currentNote = note

    private val mutableProgressFlow= MutableStateFlow(false)
    val progressFlow: Flow<Boolean> = mutableProgressFlow

    fun onBtnSaveNoteClick(newNote: Note) {
        if (currentNote == newNote || mutableProgressFlow.value) {
            return
        }

        viewModelScope.launch {
            mutableProgressFlow.value = true

            repository.addNote(newNote.copy(
                changeDate = Date(System.currentTimeMillis())
            ))

            currentNote = newNote
            mutableProgressFlow.value = false
            // тут ще б отробку успішності додавання, але це вже виходить за рамки тестового.
        }
    }
}
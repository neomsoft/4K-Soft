package io.neomsoft.a4k_soft.presentation.notes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.neomsoft.a4k_soft.data.entities.Note
import io.neomsoft.a4k_soft.data.entities.response.Response
import io.neomsoft.a4k_soft.data.repository.IDataRepository
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.util.*

class NotesViewModel(
    private val repository: IDataRepository,
) : ViewModel() {

    private val mutableNotesFlow = MutableStateFlow<Response<List<Note>>>(Response.Loading())
    val notesFlow: StateFlow<Response<List<Note>>> = mutableNotesFlow

    val noInternet: Flow<SingleLiveEvent<Boolean>> = mutableNotesFlow
        .filter { it is Response.ErrorNetworkConnection && it.cacheData != null }
        .map { SingleLiveEvent(true) }

    init {
        repository
            .getNotes()
            .onEach { mutableNotesFlow.value = it }
            .launchIn(viewModelScope)
    }

    fun onBtnAddNewNoteClick() {
        viewModelScope.launch {
            val currentTime = System.currentTimeMillis()

            val note = Note(
                id = currentTime.toString(),
                title = "Новая заметка",
                description = "",
                changeDate = Date(currentTime)
            )

            repository.addNote(note)
            // тут ще б отробку успішності додавання, але це вже виходить за рамки тестового.
        }
    }

    fun onItemDeleted(note: Note) {
        viewModelScope.launch {
            repository.deleteNote(note)
            // тут ще б отробку успішності додавання, але це вже виходить за рамки тестового.
        }
    }
}
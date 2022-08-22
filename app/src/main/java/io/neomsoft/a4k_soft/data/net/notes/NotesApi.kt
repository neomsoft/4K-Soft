package io.neomsoft.a4k_soft.data.net.notes

import io.neomsoft.a4k_soft.data.net.notes.entities.Note

interface NotesApi {

    suspend fun getNotes(): List<Note>

    suspend fun addNote(note: Note)

    suspend fun removeNote(noteId: String)
}
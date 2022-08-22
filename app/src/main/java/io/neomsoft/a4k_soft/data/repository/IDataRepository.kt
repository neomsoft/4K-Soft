package io.neomsoft.a4k_soft.data.repository

import io.neomsoft.a4k_soft.data.entities.Note
import io.neomsoft.a4k_soft.data.entities.response.Response
import kotlinx.coroutines.flow.Flow

interface IDataRepository {

    fun getNotes(): Flow<Response<List<Note>>>

    suspend fun addNote(note: Note): Response<Note>

    suspend fun updateNote(note: Note): Response<Note>

    suspend fun deleteNote(note: Note): Response<Note>
}
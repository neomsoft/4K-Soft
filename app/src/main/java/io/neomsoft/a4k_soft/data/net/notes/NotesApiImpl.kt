package io.neomsoft.a4k_soft.data.net.notes

import android.annotation.SuppressLint
import io.neomsoft.a4k_soft.data.net.notes.entities.Note
import io.neomsoft.a4k_soft.data.room.AppDatabase
import io.neomsoft.a4k_soft.data.room.entities.NoteEntity
import kotlinx.coroutines.delay
import java.text.SimpleDateFormat

class NotesApiImpl(
    private val database: AppDatabase,
): NotesApi {

    override suspend fun addNote(note: Note) {
        delay(5000)
    }

    override suspend fun removeNote(noteId: String) {
        delay(5000)
    }

    override suspend fun getNotes(): List<Note> {
        delay(5000)

        // Це тільки для імінації даних від сервера.
        return getDataFromDb().takeIf { it.isNotEmpty() } ?: getData()
    }

    @SuppressLint("SimpleDateFormat")
    private fun getDataFromDb() = database.notesDao.getAll().map {
        Note(
            id = it.id,
            title = it.title,
            description = it.description,
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(it.changeDate),
        )
    }

    @SuppressLint("SimpleDateFormat")
    private fun getData() = listOf(
        Note(
            id = "1110",
            title = "title 0",
            description = "description 0",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(1660923575000),
        ),
        Note(
            id = "1111",
            title = "title 1",
            description = "description 1",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(1660837175000),
        ),
        Note(
            id = "1112",
            title = "title 2",
            description = "description 2",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1113",
            title = "title 3",
            description = "description 3",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1114",
            title = "title 4",
            description = "description 4",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1115",
            title = "title 5",
            description = "description 5",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1116",
            title = "title 6",
            description = "description 6",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1117",
            title = "title 7",
            description = "description 7",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1118",
            title = "title 8",
            description = "description 8",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        ),
        Note(
            id = "1119",
            title = "title 9",
            description = "description 9",
            changeDate = SimpleDateFormat(Note.DATE_FORMAT).format(System.currentTimeMillis()),
        )
    )
}
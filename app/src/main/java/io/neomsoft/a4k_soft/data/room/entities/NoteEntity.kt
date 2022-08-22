package io.neomsoft.a4k_soft.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import io.neomsoft.a4k_soft.data.entities.Note
import java.util.*

@Entity(tableName = "notes")
data class NoteEntity(

    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val changeDate: Long,
) {

    companion object {
        fun from(note: Note) = NoteEntity(
            id = note.id,
            title = note.title,
            description = note.description,
            changeDate = note.changeDate.time,
        )

        fun NoteEntity.toNote() = Note(
            id = id,
            title = title,
            description = description,
            changeDate = Date(changeDate),
        )
    }
}
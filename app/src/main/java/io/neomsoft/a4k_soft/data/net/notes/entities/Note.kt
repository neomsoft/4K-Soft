package io.neomsoft.a4k_soft.data.net.notes.entities
import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import io.neomsoft.a4k_soft.data.entities.Note as MainNote

/**
 * Note.
 *
 * @param id ідентифікатор нотатки.
 * @param title основний текст нотатки.
 * @param description детальний опис нотатки.
 * @param changeDate дана останнього редагування нотатки.
 */
data class Note(
    val id: String,
    val title: String,
    val description: String,
    val changeDate: String,
) {

    companion object {
        const val DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss"

        @SuppressLint("SimpleDateFormat")
        fun Note.toNote() = MainNote(
            id = id,
            title = title,
            description = description,
            changeDate = SimpleDateFormat(DATE_FORMAT).parse(changeDate)!!,
        )

        @SuppressLint("SimpleDateFormat")
        fun from(note: MainNote) = Note(
            id = note.id,
            title = note.title,
            description = note.description,
            changeDate = SimpleDateFormat(DATE_FORMAT).format(note.changeDate.time),
        )
    }
}

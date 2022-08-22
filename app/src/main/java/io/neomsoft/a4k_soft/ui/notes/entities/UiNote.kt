package io.neomsoft.a4k_soft.ui.notes.entities

import android.text.format.DateUtils
import io.neomsoft.a4k_soft.data.entities.Note
import java.util.*

data class UiNote(
    val note: Note,
    val isTodayNote: Boolean,
) {

    companion object {

        fun from(note: Note) = UiNote(
            note = note,
            isTodayNote = DateUtils.isToday(note.changeDate.time),
        )
    }
}
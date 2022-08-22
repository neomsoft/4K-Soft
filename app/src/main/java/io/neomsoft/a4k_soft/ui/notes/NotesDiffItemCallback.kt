package io.neomsoft.a4k_soft.ui.notes

import androidx.recyclerview.widget.DiffUtil
import io.neomsoft.a4k_soft.ui.notes.entities.UiNote

object NotesDiffItemCallback : DiffUtil.ItemCallback<UiNote>() {

    override fun areItemsTheSame(oldItem: UiNote, newItem: UiNote): Boolean {
        return oldItem.note.id == newItem.note.id
    }

    override fun areContentsTheSame(oldItem: UiNote, newItem: UiNote): Boolean {
        return oldItem == newItem
    }
}
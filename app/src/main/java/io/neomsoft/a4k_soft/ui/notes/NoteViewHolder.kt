package io.neomsoft.a4k_soft.ui.notes

import android.annotation.SuppressLint
import android.view.View
import androidx.core.view.isVisible
import androidx.navigation.Navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import io.neomsoft.a4k_soft.databinding.ItemNoteBinding
import io.neomsoft.a4k_soft.ui.notes.entities.UiNote
import java.text.SimpleDateFormat

class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val binding: ItemNoteBinding by viewBinding()

    @SuppressLint("SimpleDateFormat")
    fun bind(item: UiNote) {
        binding.tvTitle.text = item.note.title
        binding.tvDescription.text = item.note.description
        binding.tvDescription.isVisible = item.note.description.isNotEmpty()

        val dateFormat = if (item.isTodayNote)
            TODAY_CHANGE_DATE_FORMAT
        else
            OTHER_CHANGE_DATE_FORMAT

        binding.tvDate.text = SimpleDateFormat(dateFormat).format(item.note.changeDate)

        itemView.setOnClickListener {
            val directions = NotesFragmentDirections.toEditNote(item.note)
            findNavController(itemView).navigate(directions)
        }
    }

    private companion object {
        const val TODAY_CHANGE_DATE_FORMAT = "hh:mm"
        const val OTHER_CHANGE_DATE_FORMAT = "dd.MM.yyyy"
    }
}
package io.neomsoft.a4k_soft.ui.notes

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.google.android.material.snackbar.Snackbar
import io.neomsoft.a4k_soft.R
import io.neomsoft.a4k_soft.data.entities.response.Response
import io.neomsoft.a4k_soft.databinding.FragmentNotesBinding
import io.neomsoft.a4k_soft.extensions.interval
import io.neomsoft.a4k_soft.presentation.notes.NotesViewModel
import io.neomsoft.a4k_soft.ui.base.BaseFragment
import io.neomsoft.a4k_soft.ui.notes.entities.UiNote
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class NotesFragment : BaseFragment(R.layout.fragment_notes) {

    private val model: NotesViewModel by viewModel()
    private val binding: FragmentNotesBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = NotesAdapter(view.context)
        val itemMargin = view.resources.getDimension(R.dimen.margin_item_note).toInt()

        binding.recyclerView.adapter = adapter
        binding.recyclerView.layoutManager = LinearLayoutManager(view.context)
        binding.recyclerView.addItemDecoration(ItemDecoration(itemMargin))

        val callback = NotesTouchCallback { model.onItemDeleted(adapter.currentList[it].note) }
        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.recyclerView)

        binding.fabAddNewNote.setOnClickListener {
            model.onBtnAddNewNoteClick()
        }

        subscribeToNoInternet(view)
        subscribeToNotes(adapter, callback)
    }

    private fun subscribeToNoInternet(view: View) {
        model.noInternet
            .onEach { Snackbar
                .make(view, R.string.fragment_notes_no_internet_error, Snackbar.LENGTH_SHORT)
                .show()
            }
            .launchOn(Lifecycle.State.RESUMED)
    }

    private fun subscribeToNotes(adapter: NotesAdapter, callback: NotesTouchCallback) {
        // Оновлюємо дані кожну секунду для того щоб змінилося відображення
        // дати в елементах при переході на наступний день.
        interval(1000)
            .combine(model.notesFlow) { _, notes -> notes }
            .filter { it is Response.Success || it.cacheData != null }
            .map { if (it is Response.Success) it.data else it.cacheData!! }
            .onEach { data ->
                adapter.submitList(data.map { UiNote.from(it) })
            }
            .launchOn(Lifecycle.State.RESUMED)

        model.notesFlow
            .onEach {
                val info = it.cacheData == null
                        && (it is Response.ErrorNetworkConnection || it is Response.Empty)

                if (it is Response.ErrorNetworkConnection) {
                    binding.tvInfo.text = getText(R.string.fragment_notes_no_internet_error)
                }

                if (it is Response.Empty) {
                    binding.tvInfo.text = getText(R.string.fragment_notes_no_data)
                }

                binding.tvInfo.isVisible = info
                binding.progress.isVisible = it is Response.Loading && it.cacheData == null
                binding.progressLinear.isVisible = it is Response.Loading && it.cacheData != null
                binding.recyclerView.isVisible = it is Response.Success || it.cacheData != null
                binding.fabAddNewNote.isVisible = it is Response.Success

                // Дозволяємо видаляти тільки коли є підключення до нету
                callback.canSwipe = it is Response.Success
            }
            .launchOn(Lifecycle.State.RESUMED)
    }
}
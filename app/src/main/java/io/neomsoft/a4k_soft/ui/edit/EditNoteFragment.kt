package io.neomsoft.a4k_soft.ui.edit

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import by.kirich1409.viewbindingdelegate.viewBinding
import io.neomsoft.a4k_soft.R
import io.neomsoft.a4k_soft.databinding.FragmentEditNoteBinding
import io.neomsoft.a4k_soft.extensions.hideKeyboard
import io.neomsoft.a4k_soft.presentation.edit.EditNoteViewModel
import io.neomsoft.a4k_soft.ui.base.BaseFragment
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class EditNoteFragment : BaseFragment(R.layout.fragment_edit_note) {

    private val args: EditNoteFragmentArgs by navArgs()
    private val model: EditNoteViewModel by viewModel { parametersOf(args.note) }
    private val binding: FragmentEditNoteBinding by viewBinding()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        createMenu()

        binding.tvTitle.setText(args.note.title)
        binding.tvDescription.setText(args.note.description)

        model.progressFlow
            .onEach {
                binding.progress.isVisible = it
                if (it) hideKeyboard()
            }
            .launchOn(Lifecycle.State.RESUMED)
    }

    private fun createMenu() {
        val menuHost: MenuHost = requireActivity()

        menuHost.addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.edit_note_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem) = when (menuItem.itemId) {
                R.id.save -> {
                    model.onBtnSaveNoteClick(args.note.copy(
                        title = binding.tvTitle.text.toString().trim(),
                        description = binding.tvDescription.text.toString().trim()
                    ))
                    true
                }
                else -> false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}
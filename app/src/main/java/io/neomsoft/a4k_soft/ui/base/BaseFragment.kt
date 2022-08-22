package io.neomsoft.a4k_soft.ui.base

import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

open class BaseFragment(@LayoutRes contentLayoutId: Int) : Fragment(contentLayoutId) {

    protected fun <T> Flow<T>.launchOn(state: Lifecycle.State) {
        lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(state) {
                this@launchOn.collect()
            }
        }
    }
}
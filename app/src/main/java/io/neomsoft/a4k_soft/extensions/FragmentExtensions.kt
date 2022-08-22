package io.neomsoft.a4k_soft.extensions

import android.app.Activity
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment

fun Fragment.hideKeyboard() {
    activity
        ?.getSystemService(Activity.INPUT_METHOD_SERVICE)
        ?.let { it as InputMethodManager }
        ?.hideSoftInputFromWindow(this.view?.windowToken, 0)
}
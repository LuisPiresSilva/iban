package net.luispiressilva.iban.utils.manager

import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import javax.inject.Inject

class KeyboardManager @Inject constructor() {
    companion object {
        private const val HIDE_SOFT_INPUT_FLAGS_NONE = 0
    }

    fun hide(activity: Activity) {
        activity.currentFocus?.let {
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(it.windowToken, HIDE_SOFT_INPUT_FLAGS_NONE)
        }
    }
}
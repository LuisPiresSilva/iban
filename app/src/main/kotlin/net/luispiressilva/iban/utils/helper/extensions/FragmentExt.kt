package net.luispiressilva.iban.utils.helper.extensions

import android.content.Intent
import androidx.fragment.app.Fragment
import net.luispiressilva.iban.utils.helper.DebounceTimer

/**
 * starts an activity using a debouncer (fast click avoidance)
 */
fun Fragment.startActivityDebounced(intent : Intent, debounceTimer: DebounceTimer) {
    debounceTimer.debounceRunFirst {
        startActivity(intent)
    }
}
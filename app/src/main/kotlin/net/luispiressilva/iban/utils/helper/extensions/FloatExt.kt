package net.luispiressilva.iban.utils.helper.extensions

import android.content.Context

fun Float.toDp(context: Context?): Float {
    return this * (context?.resources?.displayMetrics?.density ?: 0F)
}
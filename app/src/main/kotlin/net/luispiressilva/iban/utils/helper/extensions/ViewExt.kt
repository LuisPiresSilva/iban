package net.luispiressilva.iban.utils.helper.extensions

import android.view.View


fun View.removeView() {
    visibility = View.GONE
}

fun View.hideView() {
    visibility = View.INVISIBLE
}

fun View.showView() {
    visibility = View.VISIBLE
}
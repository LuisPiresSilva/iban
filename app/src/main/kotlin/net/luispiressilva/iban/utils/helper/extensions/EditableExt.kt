package net.luispiressilva.iban.utils.helper.extensions

import android.text.Editable
import java.lang.NumberFormatException

/**
 * @return the Integer for a given String
 */
fun Editable?.toInt(): Int {
    return try {
        Integer.parseInt(this.toString())
    } catch (e : NumberFormatException) {
        -1
    }
}
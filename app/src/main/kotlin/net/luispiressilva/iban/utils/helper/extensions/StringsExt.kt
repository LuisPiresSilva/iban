package net.luispiressilva.iban.utils.helper.extensions

import java.lang.NumberFormatException

/**
 * @return the Integer for a given String
 */
fun String?.toInt(): Int {
    return try {
        Integer.parseInt(this.toString())
    } catch (e : NumberFormatException) {
        -1
    }
}
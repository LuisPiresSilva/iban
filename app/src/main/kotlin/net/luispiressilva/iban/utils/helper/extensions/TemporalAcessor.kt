package net.luispiressilva.iban.utils.helper.extensions

import org.threeten.bp.format.DateTimeFormatter
import org.threeten.bp.temporal.TemporalAccessor


/**
 * returns string representation using the passed formatter
 */
fun TemporalAccessor.toString(formatter: DateTimeFormatter): String {
    return formatter.format(this)
}
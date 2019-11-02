package net.luispiressilva.iban.utils.helper.extensions

import org.threeten.bp.LocalDateTime

/**
 * creates a LocalDateTime with all time values as zero
 */
fun LocalDateTime.withZeroTime() : LocalDateTime {
    return this.withHour(0).withMinute(0).withSecond(0).withNano(0)
}
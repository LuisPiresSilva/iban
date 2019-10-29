package net.luispiressilva.iban.utils.helper.extensions

import org.threeten.bp.LocalDateTime

/**
 * starts an activity for result using a debouncer (fast click avoidance)
 */
fun LocalDateTime.withZeroTime() : LocalDateTime {
    return this.withHour(0).withMinute(0).withSecond(0).withNano(0)
}
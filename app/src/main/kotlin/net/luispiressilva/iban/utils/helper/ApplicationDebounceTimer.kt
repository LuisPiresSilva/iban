package net.luispiressilva.iban.utils.helper

import android.util.Log
import java.util.*

class ApplicationDebounceTimer(private var timerFirst: Timer = Timer()
                               , private var timerLast: Timer = Timer()) {

    private val DEFAULT_DELAY = 750L


    private var canRun = true
    fun debounceRunFirst(milWait: Long = DEFAULT_DELAY, block: () -> Unit) {
        if(canRun) {
            canRun = false
            block.invoke()
            timerLast.cancel()
            timerLast = Timer()
            timerLast.schedule(
                    object : TimerTask() {
                        override fun run() {
                            canRun = true
                        }
                    },
                    milWait
            )
        } else {
            Log.w(javaClass.simpleName , "Block was debounced!")
        }
    }

    fun debounceRunLast(milWait: Long = DEFAULT_DELAY, block: () -> Unit) {
        timerFirst.cancel()
        timerFirst = Timer()
        timerFirst.schedule(
                object : TimerTask() {
                    override fun run() {
                        block.invoke()
                    }
                },
                milWait
        )
    }

}
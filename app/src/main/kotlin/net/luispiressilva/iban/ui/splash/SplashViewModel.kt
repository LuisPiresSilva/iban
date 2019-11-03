package net.luispiressilva.iban.ui.splash

import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SplashViewModel @Inject constructor(): ViewModel() {

    val WAITING_TIME = 3000L
    var timerStarted : Boolean = false


}
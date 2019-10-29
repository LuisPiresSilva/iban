package net.luispiressilva.iban.ui.splash

import android.os.Handler
import net.luispiressilva.iban.R
import net.luispiressilva.iban.ui.base.BaseActivity
import net.luispiressilva.iban.utils.helper.DebounceTimer
import net.luispiressilva.iban.utils.helper.extensions.startActivityDebounced
import net.luispiressilva.iban.databinding.ActivitySplashBinding

/**
 * Activity to apply the splash screen.
 */
class SplashActivity : BaseActivity<ActivitySplashBinding, SplashViewModel>() {

    override fun layoutToInflate() = R.layout.activity_splash

    override fun getViewModelClass() = SplashViewModel::class.java

    private val navDebounceTimer: DebounceTimer by lazy {
        DebounceTimer(lifecycle)
    }


    override fun doOnCreated() {
        if(viewModel.timerStarted.not()) {
            viewModel.timerStarted = true
            Handler().postDelayed({
                navigate()
            }, viewModel.WAITING_TIME)
        }
    }


    private fun navigate() {
        startActivityDebounced(manager.navigateGistList(this), navDebounceTimer)
    }


}

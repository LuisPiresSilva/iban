package net.luispiressilva.iban.ui.splash

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
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

    override var shouldFullScreenStrech = true

    override fun layoutToInflate() = R.layout.activity_splash

    override fun getViewModelClass() = SplashViewModel::class.java

    private val navDebounceTimer: DebounceTimer by lazy {
        DebounceTimer(lifecycle)
    }


    override fun doOnCreated() {

        if(viewModel.timerStarted.not()) {
            viewModel.timerStarted = true
            playLogoAnimation()
            Handler().postDelayed({
                navigate()
            }, viewModel.WAITING_TIME)
        }
    }


    private fun navigate() {
        startActivityDebounced(manager.navigateGistList(this), navDebounceTimer)
    }


    fun playLogoAnimation() {
        val scaleX = ObjectAnimator.ofFloat(dataBinding.splashLogoImage,
            "scaleX", 1f).setDuration(viewModel.WAITING_TIME - 300)
        val scaleY = ObjectAnimator.ofFloat(dataBinding.splashLogoImage,
            "scaleY", 1f).setDuration(viewModel.WAITING_TIME - 300)

        val animationSet = AnimatorSet()
        animationSet.play(scaleX).with(scaleY)
        animationSet.start()
    }

}

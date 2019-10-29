package net.luispiressilva.iban.ui.gist_detail

import net.luispiressilva.iban.R
import net.luispiressilva.iban.databinding.ActivityGistDetailBinding
import net.luispiressilva.iban.ui.base.BaseActivity
import net.luispiressilva.iban.utils.helper.DebounceTimer

/**
 * Activity to apply the splash screen.
 */
class GistDetailActivity : BaseActivity<ActivityGistDetailBinding, GistDetailViewModel>() {

    override fun layoutToInflate() = R.layout.activity_gist_detail

    override fun getViewModelClass() = GistDetailViewModel::class.java

    private val navDebounceTimer: DebounceTimer by lazy {
        DebounceTimer(lifecycle)
    }


    override fun doOnCreated() {

    }


    private fun navigate() {
//        startActivityDebounced(manager.navigateGistList(this), navDebounceTimer)
    }


}

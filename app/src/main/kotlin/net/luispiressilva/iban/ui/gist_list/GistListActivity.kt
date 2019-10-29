package net.luispiressilva.iban.ui.gist_list

import net.luispiressilva.iban.R
import net.luispiressilva.iban.databinding.ActivityGistListBinding
import net.luispiressilva.iban.ui.base.BaseActivity
import net.luispiressilva.iban.utils.helper.DebounceTimer

/**
 * Activity to apply the splash screen.
 */
class GistListActivity : BaseActivity<ActivityGistListBinding, GistListViewModel>() {

    override fun layoutToInflate() = R.layout.activity_gist_list

    override fun getViewModelClass() = GistListViewModel::class.java

    private val navDebounceTimer: DebounceTimer by lazy {
        DebounceTimer(lifecycle)
    }


    override fun doOnCreated() {

    }


    private fun navigate() {
//        startActivityDebounced(manager.navigateGistList(this), navDebounceTimer)
    }


}

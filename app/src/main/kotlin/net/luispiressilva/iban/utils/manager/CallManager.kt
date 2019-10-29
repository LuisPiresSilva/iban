package net.luispiressilva.iban.utils.manager

import android.app.Application
import android.content.Context
import android.content.Intent
import net.luispiressilva.iban.ui.gist_detail.GistDetailActivity
import net.luispiressilva.iban.ui.gist_list.GistListActivity
import javax.inject.Inject

class CallManager @Inject constructor(val application: Application) {


    fun navigateGistList(context: Context?): Intent {
        val intent = Intent(context, GistListActivity::class.java)

        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK


        return intent
    }


    fun navigateGistDetail(context: Context?): Intent {
        val intent = Intent(context, GistDetailActivity::class.java)


        return intent
    }

}
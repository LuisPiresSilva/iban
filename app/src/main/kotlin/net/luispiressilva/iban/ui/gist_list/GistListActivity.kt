package net.luispiressilva.iban.ui.gist_list

import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.iban.R
import net.luispiressilva.iban.data.common.Status
import net.luispiressilva.iban.databinding.ActivityGistListBinding
import net.luispiressilva.iban.ui.base.BaseActivity
import net.luispiressilva.iban.ui.gist_list.list.GistsRecyclerViewAdapter
import net.luispiressilva.iban.utils.decoration.SpaceItemDecorations
import net.luispiressilva.iban.utils.helper.DebounceTimer
import net.luispiressilva.iban.utils.helper.extensions.startActivityDebounced
import net.luispiressilva.iban.utils.helper.extensions.toDp

/**
 * Activity to apply the splash screen.
 */
class GistListActivity : BaseActivity<ActivityGistListBinding, GistListViewModel>() {

    override fun layoutToInflate() = R.layout.activity_gist_list

    override fun getViewModelClass() = GistListViewModel::class.java

    private val navDebounceTimer: DebounceTimer by lazy { DebounceTimer(lifecycle) }

    private val gistsAdapter = GistsRecyclerViewAdapter()

    override fun doOnCreated() {
        setToolbar(dataBinding.gistListToolbar)

        dataBinding.gistList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(SpaceItemDecorations.sectionedVerticalSpaceItemDecoration(this, 12F.toDp(this@GistListActivity)))

            itemAnimator?.changeDuration = 0
            isNestedScrollingEnabled = false

            adapter = gistsAdapter
        }

        gistsAdapter.clickListener = {gist ->
            gist?.let {
                startActivityDebounced(manager.navigateGistDetail(this, it.gist.id), navDebounceTimer)
            }
        }

        viewModel.getJackeWartonPublicGists().observeNetwork(this, Observer {
            it.connection?.let { network ->
                viewModel.dataBoundResource = network
            }

            when (it.status.status) {
                Status.LOADING -> {
                    if(gistsAdapter.itemCount <= 0) {
                        dataBinding.gistListPullRefresh.isRefreshing = true
                    }
                }
                Status.FAILED -> {
                    dataBinding.gistListPullRefresh.isRefreshing = false
                    if(gistsAdapter.itemCount <= 0) {
                        it.status.error?.errorMessage?.let { message ->
                            showError(message)
                        }
                    }
                }
                Status.SUCCESS -> {
                    dataBinding.gistListPullRefresh.isRefreshing = false
                }
            }
        })

        viewModel.getJackeWartonPublicGists().observeDatabase(this, Observer {
            if(!it.data.isNullOrEmpty()) {
                dataBinding.gistListPullRefresh.isRefreshing = false
            }
            //we receive the data from the database and this is a livedata it will update if changes occur
            it.data?.let { list ->
                gistsAdapter.setList(list)
            }
        })

        dataBinding.gistListPullRefresh.setOnRefreshListener {
            refresh()
        }
    }

    private fun refresh() {
        viewModel.dataBoundResource.retry()
    }

}

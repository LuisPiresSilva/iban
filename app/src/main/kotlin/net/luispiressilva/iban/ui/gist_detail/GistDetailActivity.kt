package net.luispiressilva.iban.ui.gist_detail

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import net.luispiressilva.iban.utils.helper.DimensionHelper
import net.luispiressilva.iban.R
import net.luispiressilva.iban.app.API_TIMEOUT
import net.luispiressilva.iban.app.glide.GlideApp
import net.luispiressilva.iban.data.configs.IbanConfigs.Companion.formatter_yyyy_MM_dd_HH_mm_ss
import net.luispiressilva.iban.data.configs.IbanConfigs.Companion.formatter_yyyy_MM_dd_T_HH_mm_ss_Z
import net.luispiressilva.iban.database.model.gist.Gist
import net.luispiressilva.iban.databinding.ActivityGistDetailBinding
import net.luispiressilva.iban.ui.base.BaseActivity
import net.luispiressilva.iban.ui.gist_detail.list.GistFileRecyclerViewAdapter
import net.luispiressilva.iban.utils.decoration.SpaceItemDecorations
import net.luispiressilva.iban.utils.helper.DebounceTimer
import net.luispiressilva.iban.utils.helper.InsetsListener
import net.luispiressilva.iban.utils.helper.extensions.*
import org.threeten.bp.format.DateTimeParseException

/**
 * Activity to apply the splash screen.
 */
class GistDetailActivity : BaseActivity<ActivityGistDetailBinding, GistDetailViewModel>() {

    override var shouldFullScreenStrech = true

    companion object {
        const val ID = "ID"
    }

    override fun layoutToInflate() = R.layout.activity_gist_detail

    override fun getViewModelClass() = GistDetailViewModel::class.java

    private val insetsListener: InsetsListener by lazy { InsetsListener() }
    private val navDebounceTimer: DebounceTimer by lazy { DebounceTimer(lifecycle) }

    private lateinit var dimensionHelper: DimensionHelper
    private var deviceWidth = 0
    private val gistFilesAdapter = GistFileRecyclerViewAdapter()

    override fun doOnCreated() {
        //Acounts for status bar therefore we increase height
        insetsListener.listenStatusHeightChange(dataBinding.collapsingToolbarLayout) { v, insets ->
            val params =
                dataBinding.gistDetailToolbar.layoutParams as (ViewGroup.MarginLayoutParams)
            params.height = actionBarSize().toInt() + insets.systemWindowInsetTop
            dataBinding.gistDetailToolbar.setPadding(0, insets.systemWindowInsetTop, 0, 0)
        }

        dimensionHelper = DimensionHelper(this)

        deviceWidth = DimensionHelper.getDisplayWidth(this)

        setSupportActionBar(dataBinding.gistDetailToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        dataBinding.gistDetailToolbar.setNavigationOnClickListener { onBackPressed() }

        //dumb android bug -> back button gets hidden behind image so we raise the collapsing z
        dataBinding.gistDetailToolbar.z = 100F

        dataBinding.gistDetailFilesList.apply {
            layoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
            addItemDecoration(SpaceItemDecorations.sectionedVerticalSpaceItemDecoration(this, 12F.toDp(this@GistDetailActivity)))

            itemAnimator?.changeDuration = 0
            isNestedScrollingEnabled = false

            adapter = gistFilesAdapter
        }

        gistFilesAdapter.clickListener = {file ->
            try {
                startActivityDebounced(Intent(Intent.ACTION_VIEW, Uri.parse(file?.raw_url)), navDebounceTimer)
            } catch (e: ActivityNotFoundException) {
                //do something
                e.printStackTrace()
            }
        }

        intent?.extras?.let { bundle ->
            if (bundle.containsKey(ID)) {
                viewModel.getGist(bundle.getString(ID)).observeDatabase(this, Observer {
                    it.data?.let { gist ->
                        bindData(gist)
                    }
                })
            }
        }
    }


    private fun getHeight(
        context: Context,
        deviceWidth: Int,
        text: CharSequence,
        textSize: Int,
        typeface: Typeface,
        padding: Int
    ): Int {
        val textView = TextView(context)
        textView.setPadding(padding, 0, padding, padding)
        textView.typeface = typeface
        textView.setText(text, TextView.BufferType.SPANNABLE)
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())
        val widthMeasureSpec =
            View.MeasureSpec.makeMeasureSpec(deviceWidth, View.MeasureSpec.AT_MOST)
        val heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED)
        textView.measure(widthMeasureSpec, heightMeasureSpec)
        return textView.measuredHeight
    }

    private fun bindData(gist: Gist) {
        dataBinding.gistDetailLinearContainer.setPadding(
            0, 0, 0,
            getHeight(
                this, deviceWidth,
                "Gist ${gist.gist.id}", 24, Typeface.DEFAULT, 0
            ) + 60F.toDp(this).toInt()
        )

        GlideApp.with(this)
            .load(gist.gist.owner.avatarUrl)
            .timeout(API_TIMEOUT.toInt())
            .centerCrop()
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(dataBinding.gistDetailImage)

        dataBinding.gistDetailOwnerContent.text = gist.gist.owner.login

        dataBinding.collapsingToolbarLayout.title = "Gist ${gist.gist.id}"

        dataBinding.gistDetailDescriptionContent.text = gist.gist.description

        try {
            if (!gist.gist.createdAt.isNullOrEmpty()) {
                dataBinding.gistDetailCreatedatContainer.showView()
                dataBinding.gistDetailCreatedatContent.text =
                    formatter_yyyy_MM_dd_T_HH_mm_ss_Z.parse(gist.gist.createdAt)
                        .toString(formatter_yyyy_MM_dd_HH_mm_ss)
            } else {
                dataBinding.gistDetailCreatedatContainer.removeView()
            }
        } catch (e: DateTimeParseException) {
            //do something
            e.printStackTrace()
            dataBinding.gistDetailCreatedatContainer.removeView()
        }

        try {
            if (!gist.gist.updatedAt.isNullOrEmpty()) {
                dataBinding.gistDetailUpdatedatContainer.showView()
                dataBinding.gistDetailUpdatedatContent.text =
                    formatter_yyyy_MM_dd_T_HH_mm_ss_Z.parse(gist.gist.updatedAt)
                        .toString(formatter_yyyy_MM_dd_HH_mm_ss)
            } else {
                dataBinding.gistDetailUpdatedatContainer.removeView()
            }
        } catch (e: DateTimeParseException) {
            //do something
            e.printStackTrace()
            dataBinding.gistDetailCreatedatContainer.removeView()
        }


        dataBinding.gistDetailUrlContent.text = gist.gist.htmlUrl
        dataBinding.gistDetailUrlNavigate.setOnClickListener {
            try {
                startActivityDebounced(
                    Intent(Intent.ACTION_VIEW, Uri.parse(gist.gist.htmlUrl)),
                    navDebounceTimer
                )
            } catch (e: ActivityNotFoundException) {
                //do something
                e.printStackTrace()
            }
        }


        gistFilesAdapter.setList(gist.files)
    }

}

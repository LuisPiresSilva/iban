package net.luispiressilva.iban.ui.widgets

import android.content.Context
import android.content.res.Resources
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import androidx.appcompat.widget.Toolbar
import androidx.core.content.res.ResourcesCompat
import net.luispiressilva.iban.R
import net.luispiressilva.iban.databinding.ToolbarAppBinding
import net.luispiressilva.iban.utils.helper.extensions.showView

class AppToolbar : FrameLayout {
    private var title: String = ""

    @DrawableRes
    private var icon: Int? = null

    private lateinit var dataBinding: ToolbarAppBinding

    constructor(context: Context) : super(context) {
        initLayout(null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        initLayout(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        initLayout(attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes) {
        initLayout(attrs)
    }

    fun getToolbar(): Toolbar {
        return dataBinding.toolbar
    }

    private fun initLayout(attrs: AttributeSet?) {
        initDataBinding()

        getStyledAttributes(attrs)

        applyStyles()
    }

    private fun initDataBinding() {
        val layoutInflater = LayoutInflater.from(context)
        dataBinding = ToolbarAppBinding.inflate(layoutInflater, this, true)
    }

    private fun getStyledAttributes(attrs: AttributeSet?) {
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.AppToolbar, 0, 0).apply {
                try {
                    title = getString(R.styleable.AppToolbar_title) ?: ""

                    if (hasValue(R.styleable.AppToolbar_navigation_icon)) {
                        icon = getResourceId(R.styleable.AppToolbar_navigation_icon, -1)
                    }
                } finally {
                    recycle()
                }
            }
        }
    }

    private fun applyStyles() {
        dataBinding.title.text = title
        dataBinding.toolbar.navigationIcon = ResourcesCompat.getDrawable(resources, icon ?: -1, null)
    }

    fun setToolbarIcon(res: Resources, id: Int) {
        dataBinding.toolbar.navigationIcon = ResourcesCompat.getDrawable(res, id, null)
    }

    fun setToolbarTitle(title: String) {
        this.title = title
        dataBinding.title.text = this.title
    }

    fun setToolbarRightIconOne(id: Int, clickAction : () -> Unit) {
        dataBinding.toolbarRightIconOne.setImageResource(id)
        dataBinding.title.setPadding(0,0,0,0)
        dataBinding.toolbarRightIconOneContainer.setOnClickListener { clickAction.invoke() }
        dataBinding.toolbarRightIconOneContainer.showView()
    }

    fun setToolbarRightIconTwo(id: Int, clickAction : () -> Unit) {
        dataBinding.toolbarRightIconTwo.setImageResource(id)
        dataBinding.toolbarRightIconOneContainer.setOnClickListener { clickAction.invoke() }
        dataBinding.toolbarRightIconTwoContainer.showView()
    }

    fun setToolbarRightIconThree(id: Int, clickAction : () -> Unit) {
        dataBinding.toolbarRightIconThree.setImageResource(id)
        dataBinding.toolbarRightIconOneContainer.setOnClickListener { clickAction.invoke() }
        dataBinding.toolbarRightIconThreeContainer.showView()
    }
}
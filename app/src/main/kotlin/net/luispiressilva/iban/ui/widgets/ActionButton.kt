package net.luispiressilva.iban.ui.widgets

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.annotation.DrawableRes
import net.luispiressilva.iban.R
import net.luispiressilva.iban.databinding.ButtonActionBinding

class ActionButton : FrameLayout {
    private var text: String = ""

    private var color: String = "#000000"

    @DrawableRes
    private var icon: Int? = null

    private lateinit var dataBinding: ButtonActionBinding

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

    private fun initLayout(attrs: AttributeSet?) {
        initDataBinding()

        getStyledAttributes(attrs)

        applyStyles()
    }

    private fun initDataBinding() {
        val layoutInflater = LayoutInflater.from(context)
        dataBinding = ButtonActionBinding.inflate(layoutInflater, this, true)
    }

    private fun getStyledAttributes(attrs: AttributeSet?) {
        attrs?.let {
            context.theme.obtainStyledAttributes(it, R.styleable.ActionButton, 0, 0).apply {
                try {
                    text = getString(R.styleable.ActionButton_text) ?: ""

                    if (hasValue(R.styleable.ActionButton_text_color)) {
                        color = getString(R.styleable.ActionButton_text_color) ?: "#000000"
                    }
                    if (hasValue(R.styleable.ActionButton_icon)) {
                        icon = getResourceId(R.styleable.ActionButton_icon, -1)
                    }
                } finally {
                    recycle()
                }
            }
        }
    }

    private fun applyStyles() {
        dataBinding.text.text = text
        setTextColor(color)

        icon?.let {
            dataBinding.icon.visibility = View.VISIBLE
            dataBinding.icon.setImageDrawable(context.getDrawable(it))
        }
    }

    fun setText(value: String) {
        text = value
        dataBinding.text.text = value
    }

    fun setTextColor(colorRef: String) {
        this.color = colorRef
        dataBinding.text.setTextColor(Color.parseColor(color))
    }
}
package net.luispiressilva.iban.ui.dialog

import android.graphics.drawable.InsetDrawable
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import net.luispiressilva.iban.R
import net.luispiressilva.iban.app.glide.GlideApp
import net.luispiressilva.iban.databinding.DialogMessageBinding
import net.luispiressilva.iban.ui.base.BaseDialog

class MessageDialog : BaseDialog<DialogMessageBinding, MessageDialogViewModel>() {
    companion object {
        fun newInstance(
                @DrawableRes background: Int?,
                title: String?,
                message: String?,
                picture: String?,
                actionButtonText: String?,
                onActionButtonClick: (() -> Unit)?,
                autoDismissActionButton : Boolean = true,
                secondaryButtonText: String?,
                onSecondaryButtonClick: (() -> Unit)?,
                autoDismissSecondaryButton : Boolean = true
        ): MessageDialog {
            val dialog = MessageDialog()

            dialog.background = background
            dialog.title = title
            dialog.message = message
            dialog.picture = picture
            dialog.actionButtonText = actionButtonText
            dialog.onActionButtonClick = onActionButtonClick
            dialog.dismissActionButton = autoDismissActionButton
            dialog.secondaryButtonText = secondaryButtonText
            dialog.onSecondaryButtonClick = onSecondaryButtonClick
            dialog.dismissSecondaryButton = autoDismissSecondaryButton

            return dialog
        }
    }

    @DrawableRes
    var background: Int? = null

    var title: String? = null
    var message: String? = null
    var picture: String? = null
    var actionButtonText: String? = null
    var onActionButtonClick: (() -> Unit)? = null
    var dismissActionButton: Boolean? = null
    var secondaryButtonText: String? = null
    var onSecondaryButtonClick: (() -> Unit)? = null
    var dismissSecondaryButton: Boolean? = null

//    @Inject
//    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun layoutToInflate() = R.layout.dialog_message

//    override fun defineViewModel(): MessageDialogViewModel = ViewModelProviders.of(this, viewModelFactory).get(MessageDialogViewModel::class.java)

    override fun getViewModelClass() = MessageDialogViewModel::class.java

    override fun doOnCreated() {
        isCancelable = false

        background?.let {
//            dataBinding.imageViewBackground.setImageDrawable(context!!.getDrawable(it))
            dataBinding.dialogMessageContainer.background = context!!.getDrawable(it)
        }

        title?.let {
            dataBinding.textViewTitle.visibility = View.VISIBLE
            dataBinding.textViewTitle.text = it
        }

        message?.let {
            dataBinding.textViewMessage.visibility = View.VISIBLE
            dataBinding.textViewMessage.text = it
        }

        picture?.let {
            dataBinding.imageViewPicture.visibility = View.VISIBLE
            GlideApp
                    .with(context!!)
                    .load(picture)
                    .into(dataBinding.imageViewPicture)
        }

        actionButtonText?.let {
            dataBinding.actionButton.visibility = View.VISIBLE
            dataBinding.actionButton.setText(it)
        }

        onActionButtonClick?.let { listener ->
            dataBinding.actionButton.setOnClickListener {
                listener.invoke()
                if(dismissActionButton == true) {
                    dismiss()
                }
            }
        }

        secondaryButtonText?.let {
            dataBinding.secondaryButton.visibility = View.VISIBLE
            dataBinding.secondaryButton.text = it
        }

        onSecondaryButtonClick?.let { listener ->
            dataBinding.secondaryButton.setOnClickListener {
                listener.invoke()
                if(dismissSecondaryButton == true) {
                    dismiss()
                }            }
        }
    }

    override fun doOnStarted() {
//            dialog.window!!.setBackgroundDrawable(context!!.getDrawable(R.drawable.background_dialog))
//            dialog.window!!.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)

        val insetPx = Math.round(context!!.resources.getDimension(R.dimen.margin_horizontal_popup))

        val inset = InsetDrawable(
                context!!.getDrawable(R.drawable.background_dialog),
                insetPx,
                0,
                insetPx,
                0
        )

        dialog?.window!!.setBackgroundDrawable(inset)
        dialog?.window!!.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
}
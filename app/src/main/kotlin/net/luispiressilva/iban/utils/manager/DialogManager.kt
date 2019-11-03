package net.luispiressilva.iban.utils.manager

import androidx.annotation.DrawableRes
import net.luispiressilva.iban.ui.dialog.MessageDialog

import javax.inject.Inject

class DialogManager @Inject constructor() {
    private fun generateDialog(
            @DrawableRes background: Int?,
            title: String?,
            message: String?,
            picture: String?,
            actionButtonText: String?,
            onActionButtonClick: (() -> Unit)?,
            secondaryButtonText: String?,
            onSecondaryButtonClick: (() -> Unit)?,
            autoDismissActionButton : Boolean = true,
            autoDismissSecondaryButton : Boolean = true
    ): MessageDialog {
        return MessageDialog.newInstance(
                background,
                title,
                message,
                picture,
                actionButtonText,
                onActionButtonClick,
                autoDismissActionButton,
                secondaryButtonText,
                onSecondaryButtonClick,
                autoDismissSecondaryButton
        )
    }

    fun generateErrorDialog(title: String, message: String, actionButtonText: String): MessageDialog {
        return generateDialog(null, title, message, null, actionButtonText, {}, null, null)
    }

}
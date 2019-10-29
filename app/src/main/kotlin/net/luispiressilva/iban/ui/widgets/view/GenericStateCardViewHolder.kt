package net.luispiressilva.iban.ui.widgets.view

import android.view.View
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.iban.R
import net.luispiressilva.iban.databinding.ViewholderGenericCardStateBinding
import net.luispiressilva.iban.utils.helper.extensions.removeView
import net.luispiressilva.iban.utils.helper.extensions.showView

class GenericStateCardViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val dataBinding: ViewholderGenericCardStateBinding? = DataBindingUtil.bind(itemView)

    fun bind(
        holder: GenericStateCardViewHolder,
        card: GenericStateCard?,
        clickListener: ((GenericStateCard.ClickType, GenericStateCard) -> Unit)?,
        noContentType: Int = -1
    ) {

        val binding = holder.dataBinding ?: return

        var mCard = card
        if(card == null) {
            mCard = GenericStateCard(binding.includeView.viewholderCustomViewLoading.isVisible
            ,binding.includeView.viewholderGenericViewEmptyContent.isVisible
            ,binding.includeView.viewholderGenericViewError.isVisible)
        }

        mCard?.let {
            binding.includeView.viewholderGenericView.setOnClickListener {
                if(mCard.showingLoading) {
                    clickListener?.invoke(GenericStateCard.ClickType.LOADING, mCard)
                }
                if(mCard.showingEmptyContent) {
                    clickListener?.invoke(GenericStateCard.ClickType.EMPTY_CONTENT, mCard)
                }
                if(mCard.showingError) {
                    clickListener?.invoke(GenericStateCard.ClickType.ERROR, mCard)
                }
            }

            if(noContentType != -1) {
                binding.includeView.viewholderGenericViewEmptyContent.text = itemView.context.getString(R.string.generic_no_content_text, itemView.context.getString(noContentType))
            }
            binding.includeView.viewholderGenericViewError.text = itemView.context.getString(R.string.generic_error_retry_text)

            if(mCard.showingLoading) {
                binding.includeView.viewholderCustomViewLoading.showView()
                binding.includeView.viewholderGenericViewEmptyContent.removeView()
                binding.includeView.viewholderGenericViewError.removeView()
            }
            if(mCard.showingEmptyContent && noContentType != -1) {
                binding.includeView.viewholderCustomViewLoading.removeView()
                binding.includeView.viewholderGenericViewEmptyContent.showView()
                binding.includeView.viewholderGenericViewError.removeView()
            }
            if(mCard.showingError) {
                binding.includeView.viewholderCustomViewLoading.removeView()
                binding.includeView.viewholderGenericViewEmptyContent.removeView()
                binding.includeView.viewholderGenericViewError.showView()
            }
        }
    }


}
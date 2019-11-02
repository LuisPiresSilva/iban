package net.luispiressilva.iban.ui.gist_list.list

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.iban.database.model.gist.Gist
import net.luispiressilva.iban.databinding.ViewholderGistListCardBinding

class GistsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: ViewholderGistListCardBinding? = DataBindingUtil.bind(itemView)

    fun bind(
        holder: GistsViewHolder,
        gist: Gist,
        clickListener: ((Gist) -> Unit)
    ) {

        val binding = holder.dataBinding ?: return

        binding.viewholderGistListCardGistId.text = gist.gist.id

        binding.viewholderGistListCardGistDescription.text = gist.gist.description

        itemView.setOnClickListener {
            clickListener.invoke(gist)
        }

    }


}
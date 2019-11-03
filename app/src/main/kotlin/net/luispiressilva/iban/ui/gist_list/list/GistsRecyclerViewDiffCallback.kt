package net.luispiressilva.iban.ui.gist_list.list

import androidx.recyclerview.widget.DiffUtil
import net.luispiressilva.iban.database.model.gist.Gist

class GistsRecyclerViewDiffCallback : DiffUtil.ItemCallback<Gist>() {


    override fun areItemsTheSame(oldItem: Gist, newItem: Gist): Boolean {
        return oldItem.gist.id == newItem.gist.id
    }

    override fun areContentsTheSame(oldItem: Gist, newItem: Gist): Boolean {
        return oldItem.gist.id == newItem.gist.id && oldItem.gist.description == newItem.gist.description
    }

}

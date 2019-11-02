package net.luispiressilva.iban.ui.gist_list.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.iban.R
import net.luispiressilva.iban.database.model.gist.Gist


class GistsRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var clickListener: ((Gist?) -> Unit)

    private val gistsDiff = GistsRecyclerViewDiffCallback()
    private val mDiffer = AsyncListDiffer(this, gistsDiff)


    fun setList(gists: List<Gist>) {
        mDiffer.submitList(gists)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GistsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.viewholder_gist_list_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GistsViewHolder) {
            val gist = mDiffer.currentList[position]
            holder.bind(holder, gist, clickListener)
        }

    }


    override fun getItemCount(): Int = mDiffer.currentList.size


}
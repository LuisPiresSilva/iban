package net.luispiressilva.iban.ui.gist_detail.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.iban.R
import net.luispiressilva.iban.database.model.gist.FileEntity


class GistFileRecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    lateinit var clickListener: ((FileEntity?) -> Unit)

    private val gistsDiff = GistFileRecyclerViewDiffCallback()
    private val mDiffer = AsyncListDiffer(this, gistsDiff)


    fun setList(files: List<FileEntity>) {
        mDiffer.submitList(files)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return GistFileViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.viewholder_gist_file_list_card,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is GistFileViewHolder) {
            val gist = mDiffer.currentList[position]
            holder.bind(holder, gist, clickListener)
        }

    }


    override fun getItemCount(): Int = mDiffer.currentList.size


}
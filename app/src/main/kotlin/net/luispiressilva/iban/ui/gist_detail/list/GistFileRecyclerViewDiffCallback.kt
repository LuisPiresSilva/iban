package net.luispiressilva.iban.ui.gist_detail.list

import androidx.recyclerview.widget.DiffUtil
import net.luispiressilva.iban.database.model.gist.FileEntity

class GistFileRecyclerViewDiffCallback : DiffUtil.ItemCallback<FileEntity>() {


    override fun areItemsTheSame(oldItem: FileEntity, newItem: FileEntity): Boolean {
        return oldItem.raw_url == newItem.raw_url
    }

    override fun areContentsTheSame(oldItem: FileEntity, newItem: FileEntity): Boolean {
        return oldItem.filename == newItem.filename && oldItem.size == newItem.size
    }

}

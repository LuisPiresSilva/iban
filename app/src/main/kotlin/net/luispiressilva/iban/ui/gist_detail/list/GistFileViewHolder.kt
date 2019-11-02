package net.luispiressilva.iban.ui.gist_detail.list

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import net.luispiressilva.iban.database.model.gist.FileEntity
import net.luispiressilva.iban.databinding.ViewholderGistFileListCardBinding

class GistFileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val dataBinding: ViewholderGistFileListCardBinding? = DataBindingUtil.bind(itemView)

    fun bind(
        holder: GistFileViewHolder,
        file: FileEntity,
        clickListener: ((FileEntity) -> Unit)
    ) {

        val binding = holder.dataBinding ?: return

        binding.viewholderGistFileListCardUrl.text = file.filename

        itemView.setOnClickListener {
            clickListener.invoke(file)
        }

    }


}
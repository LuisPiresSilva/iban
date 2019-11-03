package net.luispiressilva.iban.ui.gist_list.list

import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import net.luispiressilva.iban.app.API_TIMEOUT
import net.luispiressilva.iban.app.glide.GlideApp
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

        GlideApp.with(itemView)
            .load(gist.gist.owner.avatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .timeout(API_TIMEOUT.toInt())
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(binding.viewholderGistListCardGistOwnerImage)

        binding.viewholderGistListCardGistDescription.text = gist.gist.description

        itemView.setOnClickListener {
            clickListener.invoke(gist)
        }

    }


}
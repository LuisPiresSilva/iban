package net.luispiressilva.iban.ui.gist_detail

import androidx.lifecycle.ViewModel
import net.luispiressilva.iban.data.repository.gist.GistReposity
import javax.inject.Inject

class GistDetailViewModel @Inject constructor(
    private val gistReposity: GistReposity
): ViewModel() {

    fun getGist(id: String) = gistReposity.getJakeWhartonPublicGist(id)


}
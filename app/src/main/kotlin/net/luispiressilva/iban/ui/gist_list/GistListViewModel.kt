package net.luispiressilva.iban.ui.gist_list

import androidx.lifecycle.ViewModel
import net.luispiressilva.iban.data.common.DataBoundResource
import net.luispiressilva.iban.data.repository.gist.GistReposity
import net.luispiressilva.iban.database.model.gist.Gist
import javax.inject.Inject

class GistListViewModel @Inject constructor(
    private val gistReposity: GistReposity
): ViewModel() {

    lateinit var dataBoundResource: DataBoundResource<List<Gist>,*,*>

    fun getJackeWartonPublicGists() = gistReposity.getAndFetchJakeWhartonPublicGists(true)


}
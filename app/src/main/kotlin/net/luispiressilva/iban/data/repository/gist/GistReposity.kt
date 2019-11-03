package net.luispiressilva.iban.data.repository.gist

import net.luispiressilva.iban.data.common.LiveDataWrapper
import net.luispiressilva.iban.database.model.gist.Gist

/**
 * Created by Luis Silva on 30/10/2019.
 */
interface GistReposity {

    fun getAndFetchJakeWhartonPublicGists(shouldPersist : Boolean = false): LiveDataWrapper<List<Gist>>

    fun getJakeWhartonPublicGist(id : String): LiveDataWrapper<Gist>
}
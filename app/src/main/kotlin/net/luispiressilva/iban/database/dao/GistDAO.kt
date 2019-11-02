package net.luispiressilva.iban.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.luispiressilva.iban.database.base.BaseDAO
import net.luispiressilva.iban.database.model.gist.GIST_ENTITY_TABLE
import net.luispiressilva.iban.database.model.gist.Gist
import net.luispiressilva.iban.database.model.gist.GistEntity


@Dao
abstract class GistDAO : BaseDAO<GistEntity>() {

    @Query("DELETE FROM $GIST_ENTITY_TABLE")
    abstract fun deleteAll()

    @Query("SELECT * FROM $GIST_ENTITY_TABLE")
    abstract fun getAllObjects(): List<Gist>

    @Query("SELECT * FROM $GIST_ENTITY_TABLE")
    abstract fun getAllGists(): LiveData<List<Gist>>

    @Query("SELECT * FROM $GIST_ENTITY_TABLE WHERE gist_id = :id LIMIT 1")
    abstract fun getGist(id : String): LiveData<Gist>

    @Transaction
    open fun insertAll(list: List<Gist>) {
        list.forEach {
            insert(it.gist)
        }
    }


}





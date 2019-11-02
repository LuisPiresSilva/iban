package net.luispiressilva.iban.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import net.luispiressilva.iban.database.base.BaseDAO
import net.luispiressilva.iban.database.model.gist.FILE_ENTITY_TABLE
import net.luispiressilva.iban.database.model.gist.FileEntity


@Dao
abstract class GistFileDAO : BaseDAO<FileEntity>() {

    @Query("DELETE FROM $FILE_ENTITY_TABLE")
    abstract fun deleteAll()

    @Query("SELECT * FROM $FILE_ENTITY_TABLE")
    abstract fun getAllObjects(): List<FileEntity>

    @Query("SELECT * FROM $FILE_ENTITY_TABLE")
    abstract fun getAllFiles(): LiveData<List<FileEntity>>

    @Query("SELECT * FROM $FILE_ENTITY_TABLE WHERE file_raw_url = :id LIMIT 1")
    abstract fun getFiles(id : String): LiveData<FileEntity>


}





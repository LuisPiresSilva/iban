package net.luispiressilva.iban.database.model.gist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Luis Silva on 30/10/2019.
 */

const val FILE_ENTITY_TABLE = "file"
const val FILE_PREFIX = "${FILE_ENTITY_TABLE}_"

@Parcelize
@Entity(tableName = FILE_ENTITY_TABLE)
data class FileEntity(
    @PrimaryKey
    @ColumnInfo(name = "${FILE_PREFIX}raw_url") val raw_url: String,

    @ColumnInfo(name = "${GIST_PREFIX}id") val parentID: String,

    @ColumnInfo(name = "${FILE_PREFIX}filename") val filename: String?,
    @ColumnInfo(name = "${FILE_PREFIX}type") val type: String?,
    @ColumnInfo(name = "${FILE_PREFIX}language") val language: String?,
    @ColumnInfo(name = "${FILE_PREFIX}size") val size: Long = 0
) : Parcelable
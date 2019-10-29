package net.luispiressilva.iban.database.model.gist

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

const val GIST_ENTITY_TABLE = "gist"

@Parcelize //android studio gives out warning but it works fine
@Entity(tableName = GIST_ENTITY_TABLE)
data class GistEntity(
        @PrimaryKey
        @ColumnInfo(name = "is") val id: Long



) : Parcelable
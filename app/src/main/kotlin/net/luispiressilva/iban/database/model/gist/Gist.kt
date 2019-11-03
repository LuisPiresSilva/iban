package net.luispiressilva.iban.database.model.gist

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Parcelize
class Gist(

        @Embedded
        var gist: GistEntity,

        //Relations
        @Relation(parentColumn = "${GIST_PREFIX}id", entityColumn = "${GIST_PREFIX}id", entity = FileEntity::class)
        var files: List<FileEntity>

) : Parcelable
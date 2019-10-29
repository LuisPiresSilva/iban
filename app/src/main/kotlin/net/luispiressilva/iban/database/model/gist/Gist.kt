package net.luispiressilva.iban.database.model.gist

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize


@Parcelize
class Gist(

        @Embedded
        var gist: GistEntity

        //Relations
) : Parcelable
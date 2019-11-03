package net.luispiressilva.iban.database.model.gist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

const val GIST_ENTITY_TABLE = "gist"
const val GIST_PREFIX = "${GIST_ENTITY_TABLE}_"

@Parcelize
@Entity(tableName = GIST_ENTITY_TABLE, primaryKeys = arrayOf("${GIST_PREFIX}id", "${USER_PREFIX}id"))
data class GistEntity(

    @ColumnInfo(name =  "${GIST_PREFIX}id") var id: String = "",

    @ColumnInfo(name = "${GIST_PREFIX}url") var url: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}forks_url") var forksUrl: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}commits_url") var commitsUrl: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}node_id") var nodeId: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}git_pull_url") var gitPullUrl: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}git_push_url") var gitPushUrl: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}html_url") var htmlUrl: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}public") var public: Boolean? = false,
    @ColumnInfo(name = "${GIST_PREFIX}created_at") var createdAt: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}updated_at") var updatedAt: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}description") var description: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}comments") var comments: Int? = 0,
    @ColumnInfo(name = "${GIST_PREFIX}comments_url") var commentsUrl: String? = "",
    @ColumnInfo(name = "${GIST_PREFIX}truncated") var truncated: Boolean? = false,

    @Embedded
    var owner: UserEntity

) : Parcelable {
    //usually i dont do this kind of things, but having compile time errors and
    // i dont remember why this happens and dont have time to be debugging this for this exercise
    constructor() : this("","","","",""
        ,"","","",false,"","","",0
        ,"",false,
        UserEntity(
            0, "", "", "", "","","",""
            ,"","","","","",""
            ,"","","",false
        ))
}
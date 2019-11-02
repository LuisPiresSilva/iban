package net.luispiressilva.iban.database.model.gist

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Luis Silva on 30/10/2019.
 */

const val USER_ENTITY_TABLE = "user"
const val USER_PREFIX = "${USER_ENTITY_TABLE}_"

@Parcelize
@Entity(tableName = USER_ENTITY_TABLE)
data class UserEntity(
    @PrimaryKey
    @ColumnInfo(name = "${USER_PREFIX}id") val id: Int,

    @ColumnInfo(name = "${USER_PREFIX}login") val login: String?,
    @ColumnInfo(name = "${USER_PREFIX}node_id") val nodeId: String?,
    @ColumnInfo(name = "${USER_PREFIX}avatar_url") val avatarUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}gravatar_id") val gravatarId: String?,
    @ColumnInfo(name = "${USER_PREFIX}url") val url: String?,
    @ColumnInfo(name = "${USER_PREFIX}html_url") val htmlUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}followers_url") val followersUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}following_url") val followingUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}gists_url") val gistsUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}starred_url") val starredUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}subscriptions_url") val subscriptionsUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}organizations_url") val organizationsUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}repos_url") val reposUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}events_url") val eventsUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}received_events_url") val receivedEventsUrl: String?,
    @ColumnInfo(name = "${USER_PREFIX}type") val type: String?,
    @ColumnInfo(name = "${USER_PREFIX}site_admin") val siteAdmin: Boolean?
) : Parcelable
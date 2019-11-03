package net.luispiressilva.iban.network.models.gist


import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PublicGist(
    @Json(name = "url")
    val url: String?,
    @Json(name = "forks_url")
    val forksUrl: String?,
    @Json(name = "commits_url")
    val commitsUrl: String?,
    @Json(name = "id")
    val id: String?,
    @Json(name = "node_id")
    val nodeId: String?,
    @Json(name = "git_pull_url")
    val gitPullUrl: String?,
    @Json(name = "git_push_url")
    val gitPushUrl: String?,
    @Json(name = "html_url")
    val htmlUrl: String?,
    @Json(name = "public")
    val `public`: Boolean?,
    @Json(name = "created_at")
    val createdAt: String?,
    @Json(name = "updated_at")
    val updatedAt: String?,
    @Json(name = "description")
    val description: String?,
    @Json(name = "comments")
    val comments: Int?,
    @Json(name = "comments_url")
    val commentsUrl: String?,
    @Json(name = "owner")
    val owner: Owner?,
    @Json(name = "files")
    val files: Map<String, GistFile>?,
    @Json(name = "truncated")
    val truncated: Boolean?,
    @Json(name = "user")
    val user: Any?
)
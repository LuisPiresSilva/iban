package net.luispiressilva.iban.data.repository.gist

import androidx.lifecycle.LiveData
import net.luispiressilva.iban.data.common.LiveDataWrapper
import net.luispiressilva.iban.data.common.DataBoundResource
import net.luispiressilva.iban.database.IbanDatabase
import net.luispiressilva.iban.database.model.gist.FileEntity
import net.luispiressilva.iban.database.model.gist.Gist
import net.luispiressilva.iban.database.model.gist.GistEntity
import net.luispiressilva.iban.database.model.gist.UserEntity
import net.luispiressilva.iban.network.ApiProvider
import net.luispiressilva.iban.network.models.ApiResponse
import net.luispiressilva.iban.network.models.auth.AuthenticationResponse
import net.luispiressilva.iban.network.models.gist.PublicGist
import net.luispiressilva.iban.utils.helper.AppExecutors
import javax.inject.Inject

/**
 * Created by Luis Silva on 30/10/2019.
 */
class GistRepositoryImpl @Inject constructor(
    private val apiProvider: ApiProvider,
    private val appExecutors: AppExecutors,
    private val database: IbanDatabase
) : GistReposity {

    override fun getAndFetchJakeWhartonPublicGists(shouldPersist: Boolean): LiveDataWrapper<List<Gist>> {
        return object :
            DataBoundResource<List<Gist>, List<PublicGist>, AuthenticationResponse>(appExecutors) {

            override fun createCall(): LiveData<ApiResponse<List<PublicGist>>> =
                apiProvider.fetchUserPublicGists("JakeWharton")

            override fun saveCallResult(data: List<PublicGist>?) {
                database.runInTransaction {
                    data?.forEach {
                        convertGist(it, null).let {
                            database.getGistsDAO().insert(it.gist)
                            database.getGistFilesDAO().insert(it.files)
                        }
                    }
                }
            }

            override fun loadFromDatabase(): LiveData<List<Gist>> {
                return database.getGistsDAO().getAllGists()
            }

            override fun shouldRequestFromNetwork(data: List<Gist>?) = true


//            no need to refresh token
//            override fun refreshCall(): LiveData<ApiResponse<AuthenticationResponse>>? { return null }
//            override fun refreshResult(apiResponse: AuthenticationResponse?) {  }
        }.asLiveData()

    }

    override fun getJakeWhartonPublicGist(id: String): LiveDataWrapper<Gist> {
        return object :
            DataBoundResource<Gist, PublicGist, AuthenticationResponse>(appExecutors) {

            override fun loadFromDatabase(): LiveData<Gist> {
                return database.getGistsDAO().getGist(id)
            }


//            no need to refresh token
//            override fun refreshCall(): LiveData<ApiResponse<AuthenticationResponse>>? { return null }
//            override fun refreshResult(apiResponse: AuthenticationResponse?) {  }
        }.asLiveData()

    }


    //util function but can stay private if we only listen to DB
    private fun convertGist(serverGist: PublicGist?, localGist: Gist?): Gist {
        lateinit var gist: Gist

        if (localGist == null) {//NEW GIST
            gist = Gist(
                GistEntity(
                    serverGist?.id ?: "",
                    serverGist?.url ?: "",
                    serverGist?.forksUrl,
                    serverGist?.commitsUrl ?: "",
                    serverGist?.nodeId ?: "",
                    serverGist?.gitPullUrl,
                    serverGist?.gitPushUrl ?: "",
                    serverGist?.htmlUrl,
                    serverGist?.public,
                    serverGist?.createdAt,
                    serverGist?.updatedAt,
                    serverGist?.description ?: "",
                    serverGist?.comments ?: 0,
                    serverGist?.commentsUrl ?: "",
                    serverGist?.truncated,
                    UserEntity(
                        serverGist?.owner?.id ?: 0,
                        serverGist?.owner?.login ?: "",
                        serverGist?.owner?.nodeId ?: "",
                        serverGist?.owner?.avatarUrl ?: "",
                        serverGist?.owner?.gravatarId ?: "",
                        serverGist?.owner?.url ?: "",
                        serverGist?.owner?.htmlUrl ?: "",
                        serverGist?.owner?.followersUrl ?: "",
                        serverGist?.owner?.followingUrl ?: "",
                        serverGist?.owner?.gistsUrl ?: "",
                        serverGist?.owner?.starredUrl ?: "",
                        serverGist?.owner?.subscriptionsUrl ?: "",
                        serverGist?.owner?.organizationsUrl ?: "",
                        serverGist?.owner?.reposUrl ?: "",
                        serverGist?.owner?.eventsUrl ?: "",
                        serverGist?.owner?.receivedEventsUrl ?: "",
                        serverGist?.owner?.type ?: "",
                        serverGist?.owner?.siteAdmin

                    )

                ),
                serverGist?.files?.map {
                    FileEntity(
                        it.value.raw_url ?: "",
                        serverGist.id ?: "",
                        it.value.filename ?: "",
                        it.value.type,
                        it.value.language,
                        it.value.size
                    )
                } ?: mutableListOf()

            )


        } else {//UPDATE ALREADY EXISTING GIST

//          i did not implemented this part but was just to show that we should
//          handle such scenarios
//          besides giving more work and create a lot of boilerplate such approach solves a lot of problems
//          for example
//          if we save files locally and reference them in database object
//          we might want here to check if they suffer an update
//          if yes we need to delete the file from device and download the new one
//          we can delete and update right away or start the worker that handles the download here
//          update later etc
        }

        return gist
    }


}
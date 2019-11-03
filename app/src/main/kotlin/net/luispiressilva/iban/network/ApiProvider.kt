package net.luispiressilva.iban.network

import java.util.concurrent.atomic.AtomicBoolean
import javax.inject.Inject

/**
 * The Api provider, this class is responsible to manage all the connections with clients' api and user session.
 */
class ApiProvider @Inject constructor(private val service: EndpointCollection) {

    var refreshingToken : AtomicBoolean = AtomicBoolean(false)


    fun fetchUserPublicGists(userName: String) = service.fetchUserPublicGists(userName)
}

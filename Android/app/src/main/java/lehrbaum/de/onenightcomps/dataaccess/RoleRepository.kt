package lehrbaum.de.onenightcomps.dataaccess

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.GameRole

class RoleRepository {
	private val restService: RestService by inject()

	fun getRolesAsync(): Deferred<Array<GameRole>> {
		val deferred = CompletableDeferred<Array<GameRole>>()
		restService.getRoles().enqueue(deferred.generateRetrofitCallback())
		return deferred
	}
}
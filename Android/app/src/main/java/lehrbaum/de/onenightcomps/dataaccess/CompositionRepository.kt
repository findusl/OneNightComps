package lehrbaum.de.onenightcomps.dataaccess

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.Composition

@Suppress("unused")
private const val TAG = "CompositionRepository"

class CompositionRepository {
	private val restService: RestService by inject()
	fun getCompositionsAsync(): Deferred<Array<Composition>> {
		val deferred = CompletableDeferred<Array<Composition>>()
		restService.getCompositions().enqueue(deferred.generateRetrofitCallback())
		return deferred
	}

	fun createCompositionAsync(composition: Composition): Deferred<Int> {
		val deferred = CompletableDeferred<Int>()
		restService.createComposition(composition).enqueue(deferred.generateRetrofitCallback())
		return deferred
	}
}
package lehrbaum.de.onenightcomps.dataaccess

import lehrbaum.de.onenightcomps.rules.InstantExecutorExtension
import lehrbaum.de.onenightcomps.rules.LogExtension
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LogExtension::class, InstantExecutorExtension::class)
class RestServiceTest {

	private var restService: RestService = RestServiceFactory.getRestService()

	//TODO @Test first remove KoinExt.kt
	fun serverResponseValidTest() {
		val httpResponse = restService.getCompositions().execute()
		assertTrue(httpResponse.isSuccessful, "Request was not successful: " + httpResponse.code())
		assertNotNull(httpResponse.body(), "Body of response was null")
		val response = httpResponse.body()!!
		assertTrue(
			response.errorMessage.isEmpty(),
			"Not empty error message: " + response.errorMessage
		)
		assertNotNull(response.result)
		val compositions = response.result!!
		assertNotEquals(0, compositions.size, "There are no compositions")
		for (comp in compositions) {
			assertNotEquals(-1, comp.id, "Id of comp is -1")
			for (role in comp.roles) {
				assertNotEquals(-1, role.id, "Id of role is -1")
				assertNotNull(role.faction, "Faction is null")
				val faction = role.faction!!
				assertNotEquals(-1, faction.id, "Id of faction is -1")
			}
		}
	}
}

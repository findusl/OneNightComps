package lehrbaum.de.onenightcomps.dataaccess

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test

class RestServiceTest {
	@Rule
	@JvmField
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private var restService: RestService = RestServiceFactory.getRestService()

	//TODO @Test first remove KoinExt.kt
	fun serverResponseValidTest() {
		val httpResponse = restService.getCompositions().execute()
		assertTrue("Request was not successful: " + httpResponse.code(), httpResponse.isSuccessful)
		assertNotNull("Body of response was null", httpResponse.body())
		val response = httpResponse.body()!!
		assertTrue(
			"Not empty error message: " + response.errorMessage,
			response.errorMessage.isEmpty()
		)
		assertNotNull(response.result)
		val compositions = response.result!!
		assertNotEquals("There are no compositions", 0, compositions.size)
		for (comp in compositions) {
			assertNotEquals("Id of comp is -1", -1, comp.id)
			for (role in comp.roles) {
				assertNotEquals("Id of role is -1", -1, role.id)
				assertNotNull("Faction is null", role.faction)
				val faction = role.faction!!
				assertNotEquals("Id of faction is -1", -1, faction.id)
			}
		}
	}
}

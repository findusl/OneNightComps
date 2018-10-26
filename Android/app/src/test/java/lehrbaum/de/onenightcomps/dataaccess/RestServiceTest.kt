package lehrbaum.de.onenightcomps.dataaccess

import org.junit.Assert.*
import org.junit.Test

class RestServiceTest {
    private var restService : RestService = RestService.getInstance()

    @Test
    fun serverResponseValidTest() {
        val httpResponse = restService.getCompositions().execute()
        assertTrue("Request was not successful: " + httpResponse.code(), httpResponse.isSuccessful)
        assertNotNull("Body of response was null", httpResponse.body())
        val response = httpResponse.body()!!
        assertTrue("Not empty error message: " + response.errorMessage, response.errorMessage.isEmpty())
        assertNotNull(response.result)
        val compositions = response.result!!
        assertNotEquals("There are no compositions", 0, compositions.size)
        for(comp in compositions) {
            assertNotEquals("Id of comp is -1", -1, comp.id)
            assertEquals("The role count does not equal the amount of roles", comp.roleCount, comp.roles.size)
            for(role in comp.roles) {
                assertNotEquals("Id of role is -1", -1, role.id)
                assertNotNull("Faction is null", role.faction)
                val faction = role.faction!!
                assertNotEquals("Id of faction is -1", -1, faction.id)
            }
        }
    }
}

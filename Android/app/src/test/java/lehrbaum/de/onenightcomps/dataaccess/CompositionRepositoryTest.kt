package lehrbaum.de.onenightcomps.dataaccess

import androidx.core.util.Consumer
import lehrbaum.de.onenightcomps.model.Composition
import org.junit.Test
import org.junit.rules.Timeout
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.stubbing.Answer
import javax.security.auth.callback.Callback

/**
 * This test checks whether the composition repository passes errors and results correctly
 */
class CompositionRepositoryTest {
	private val networkTimeout = 10000L

	@Test
	fun noInternetHandled() {
		//as the name suggests, this test only works if there is no internet
		val callback = mock(Consumer::class.java)
		val errorResolution = Mockito.mock(Resolution::class.java)
		CompositionRepository.getCompositions(callback as Consumer<Array<Composition>>, errorResolution)
		verify(errorResolution, timeout(networkTimeout)).onServerUnavailable()
	}
}
package lehrbaum.de.onenightcomps.dataaccess

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows


/**
 * This test checks whether the composition repository passes errors and results correctly
 */
class CompositionRepositoryTest {
	private lateinit var compositionRepository: CompositionRepository

	@BeforeEach
	fun setUp() {
		//TODO inject mock UserRepository
		compositionRepository = CompositionRepository()
	}

	@Test()
	fun createComposition_notLoggedIn_exceptionThrown() {
		assertThrows<NotLoggedInException> {
			throw NotLoggedInException()
		}
	}
}
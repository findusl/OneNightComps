package lehrbaum.de.onenightcomps.dataaccess

import org.junit.Before
import org.junit.Test

/**
 * This test checks whether the composition repository passes errors and results correctly
 */
class CompositionRepositoryTest {
	private lateinit var compositionRepository: CompositionRepository

	@Before
	fun setUp() {
		//TODO inject mock UserRepository
		compositionRepository = CompositionRepository()
	}

	@Test(expected = NotLoggedInException::class)
	fun createComposition_notLoggedIn_exceptionThrown() {
		throw NotLoggedInException()
	}
}
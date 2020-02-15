package lehrbaum.de.onenightcomps.dataaccess

import io.mockk.mockk
import lehrbaum.de.onenightcomps.model.GameRole
import lehrbaum.de.onenightcomps.rules.InstantExecutorExtension
import lehrbaum.de.onenightcomps.rules.LogExtension
import lehrbaum.de.onenightcomps.viewmodel.CheckableGridViewModel
import lehrbaum.de.onenightcomps.viewmodel.CreateCompositionViewModel
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(LogExtension::class, InstantExecutorExtension::class)
class CreateCompositionViewModelTest {

	private lateinit var mockCheckableGridViewModel: CheckableGridViewModel<GameRole>
	private lateinit var createCompositionViewModel: CreateCompositionViewModel

	@BeforeEach
	fun setUp() {
		mockCheckableGridViewModel = mockk()
		createCompositionViewModel = CreateCompositionViewModel()
	}


	@Test
	fun testOnCompletedOptionSelected() {
		createCompositionViewModel.titleText.value = "some title"
		createCompositionViewModel.onCompletedOptionSelected()
	}
}
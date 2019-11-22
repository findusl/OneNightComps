package lehrbaum.de.onenightcomps.dataaccess

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.mockk
import lehrbaum.de.onenightcomps.model.GameRole
import lehrbaum.de.onenightcomps.viewmodel.CheckableGridViewModel
import lehrbaum.de.onenightcomps.viewmodel.CreateCompositionViewModel
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class CreateCompositionViewModelTest {
	@Rule
	@JvmField
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var mockCheckableGridViewModel: CheckableGridViewModel<GameRole>
	private lateinit var createCompositionViewModel: CreateCompositionViewModel

	@Before
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
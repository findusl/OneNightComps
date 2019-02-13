package lehrbaum.de.onenightcomps.viewmodel

import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import io.mockk.every
import io.mockk.mockk
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.NetworkUnavailableException
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestErrorViewModel {
	@Rule
	@JvmField
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	private lateinit var errorViewModel: ErrorViewModel

	@Before
	fun setUp() {
		errorViewModel = ErrorViewModel()
	}

	@Test
	fun testTryAndHandleException_Default_ShouldNotSetMessage() {
		errorViewModel.tryAndHandleException {

		}

		assertNull("consent message not null",
			errorViewModel.consentErrorMessage.value)
		assertNull("disappearing message not null",
			errorViewModel.disappearingErrorMessage.value)
	}

	@Test
	fun testTryAndHandleException_NetworkUnavailable_ShowErrorMessage() {
		val context = getMockContextForStringResource(
			R.string.warning_no_internet_connection, "test")

		errorViewModel.tryAndHandleException {
			throw NetworkUnavailableException()
		}

		assertEquals("disappearing message was not correct", "test",
			errorViewModel.disappearingErrorMessage.value?.invoke(context))
	}

	@Test
	fun testTryAndHandleExceptionAsync_ShowLoading_ShouldStartAndStopLoading() {
		errorViewModel.tryAndHandleExceptionAsync(true) {
			assertTrue(errorViewModel.loadingIndicatorVisibility.value == View.VISIBLE)
		}
	}

	private fun getMockContextForStringResource(resourceId: Int, stringToReturn: String): Context {
		val context = mockk<Context>()
		every { context.getString(resourceId) } returns stringToReturn
		return context
	}
}
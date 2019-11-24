package lehrbaum.de.onenightcomps.viewmodel

import android.content.Context
import android.view.View
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.NetworkUnavailableException
import lehrbaum.de.onenightcomps.rules.CoroutinesRule
import lehrbaum.de.onenightcomps.view.TextProvider
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TestErrorViewModel {
	@Rule
	@JvmField
	val instantTaskExecutorRule = InstantTaskExecutorRule()

	@ExperimentalCoroutinesApi
	@Rule
	@JvmField
	val coroutinesRule = CoroutinesRule()

	private lateinit var errorViewModel: ErrorViewModel
	private val errorViewModelDelegate = mock<GenericErrorViewModel.Delegate>()

	@Before
	fun setUp() {
		errorViewModel = ErrorViewModel()
		val lifecycle = mock<Lifecycle>() {
			on { currentState } doReturn Lifecycle.State.RESUMED
		}
		errorViewModel.setDelegate(errorViewModelDelegate, LifecycleOwner { lifecycle })
	}

	@Test
	fun testTryAndHandleException_Default_ShouldNotSetMessage() {
		errorViewModel.tryAndHandleException {}

		verify(errorViewModelDelegate, never()).showConsentError(any())
		verify(errorViewModelDelegate, never()).showDisappearingError(any())
	}

	@Test
	fun testTryAndHandleException_NetworkUnavailable_ShowErrorMessage() {
		val context = getMockContextForStringResource(
			R.string.warning_no_internet_connection, "test"
		)

		errorViewModel.tryAndHandleException {
			throw NetworkUnavailableException()
		}

		val captor = argumentCaptor<TextProvider>()

		verify(errorViewModelDelegate).showDisappearingError(captor.capture())

		assertEquals(
			"disappearing message was not correct",
			"test",
			captor.firstValue.invoke(context)
		)
	}

	@Test
	fun testTryAndHandleExceptionAsync_ShowLoading_ShouldStartAndStopLoading() {
		errorViewModel.tryAndHandleExceptionAsync(true) {
			assertTrue(errorViewModel.loadingIndicatorVisibility.value == View.VISIBLE)
		}
	}

	private fun getMockContextForStringResource(resourceId: Int, stringToReturn: String): Context {
		val context = mockk<Context>()
		every { context.getString(resourceId, *anyVararg()) } returns stringToReturn
		return context
	}
}
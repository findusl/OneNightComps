package lehrbaum.de.onenightcomps.viewmodel

import android.content.Context
import android.view.View
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.nhaarman.mockitokotlin2.*
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.runBlocking
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.NetworkUnavailableException
import lehrbaum.de.onenightcomps.rules.CoroutinesExtension
import lehrbaum.de.onenightcomps.rules.InstantExecutorExtension
import lehrbaum.de.onenightcomps.rules.LogExtension
import lehrbaum.de.onenightcomps.view.TextProvider
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith

@ObsoleteCoroutinesApi
@ExperimentalCoroutinesApi
@ExtendWith(LogExtension::class, InstantExecutorExtension::class, CoroutinesExtension::class)
class TestErrorViewModel {

	private lateinit var errorViewModel: ErrorViewModel
	private val errorViewModelDelegate = mock<GenericErrorViewModel.Delegate>()

	@BeforeEach
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
			captor.firstValue.invoke(context),
			"test",
			"disappearing message was not correct"
		)
	}

	@Test
	fun testTryAndHandleExceptionAsync_ShowLoading_ShouldStartAndStopLoading() {
		errorViewModel.loadingIndicatorVisibility.observeForever {  }
		val loadingIndicatorVisibility = CompletableDeferred<Int>()
		errorViewModel.tryAndHandleExceptionAsync(true) {
			loadingIndicatorVisibility.complete(errorViewModel.loadingIndicatorVisibility.value)
		}
		runBlocking {
			assertTrue(loadingIndicatorVisibility.await() == View.VISIBLE)
		}
	}

	private fun getMockContextForStringResource(resourceId: Int, stringToReturn: String): Context {
		val context = mockk<Context>()
		every { context.getString(resourceId, *anyVararg()) } returns stringToReturn
		return context
	}
}
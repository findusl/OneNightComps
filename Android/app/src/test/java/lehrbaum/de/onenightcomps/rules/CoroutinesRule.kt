package lehrbaum.de.onenightcomps.rules

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
class CoroutinesRule : TestWatcher() {

	private val mainThreadSurrogate = newSingleThreadContext("UI thread")

	override fun starting(description: Description?) {
		super.starting(description)
		Dispatchers.setMain(mainThreadSurrogate)
	}

	override fun finished(description: Description?) {
		super.finished(description)
		Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
		mainThreadSurrogate.close()
	}
}
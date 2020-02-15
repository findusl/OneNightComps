package lehrbaum.de.onenightcomps.rules

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.ObsoleteCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.jupiter.api.extension.AfterEachCallback
import org.junit.jupiter.api.extension.BeforeEachCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.rules.TestWatcher
import org.junit.runner.Description

@ExperimentalCoroutinesApi
@ObsoleteCoroutinesApi
class CoroutinesExtension: BeforeEachCallback, AfterEachCallback {

	private val mainThreadSurrogate = newSingleThreadContext("UI thread")

	override fun beforeEach(context: ExtensionContext?) {
		Dispatchers.setMain(mainThreadSurrogate)
	}

	override fun afterEach(context: ExtensionContext?) {
		Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
		mainThreadSurrogate.close()
	}

}
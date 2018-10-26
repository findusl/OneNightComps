package lehrbaum.de.onenightcomps.view_model

import lehrbaum.de.onenightcomps.data_access.Resolution
import java.lang.Exception
import java.lang.RuntimeException

object ResolutionProvider {
	private var _resolution: Resolution? = null
	var resolution : Resolution
		get() {
			return _resolution ?: throw ResolutionNotInitializedException()
		}
		set(value) {
			_resolution = value
		}
}

class ResolutionNotInitializedException: RuntimeException("Please initialize the resolution in the resolutionProvider.") {}
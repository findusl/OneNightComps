package lehrbaum.de.onenightcomps.viewmodel

import lehrbaum.de.onenightcomps.dataaccess.Resolution
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
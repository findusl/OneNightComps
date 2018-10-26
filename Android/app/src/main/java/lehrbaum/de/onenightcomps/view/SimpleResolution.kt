package lehrbaum.de.onenightcomps.view

import android.view.View
import com.google.android.material.snackbar.Snackbar
import lehrbaum.de.onenightcomps.data_access.Resolution

class SimpleResolution(val view: View) : Resolution {
	override fun onServerUnavailable() {
		showAsSnackbar("Server unavailable");
	}

	override fun onInternalServerError() {
		showAsSnackbar("Internal server error");
	}

	override fun onRessourceNotFound() {
		showAsSnackbar("ressource not found");
	}

	override fun onUnkownException(t: Throwable) {
		showAsSnackbar("Unkown exception. Please try again later.");
	}

	override fun onNetworkUnavailable() {
		showAsSnackbar("This operation requires internet access. Please check that you are online.");
	}

	override fun onInsufficientRights() {
		showAsSnackbar("Insufficient rights.");
	}

	fun showAsSnackbar(textToShow: String) {
		Snackbar.make(view, textToShow, Snackbar.LENGTH_LONG)
			.setAction("Action", null).show()
	}
}
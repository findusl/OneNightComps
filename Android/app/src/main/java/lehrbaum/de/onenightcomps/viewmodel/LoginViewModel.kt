package lehrbaum.de.onenightcomps.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.UserRepository
import lehrbaum.de.onenightcomps.fragments.LoginFragmentDirections
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.view.TextProvider
import lehrbaum.de.onenightcomps.view.asTextProvider

class LoginViewModel : ErrorViewModel() {
	val username = MutableLiveData<String>()
	val password = MutableLiveData<String>()

	val usernameError = MutableLiveData<TextProvider>()
	val passwordError = MutableLiveData<TextProvider>()

	private val userRepository: UserRepository by inject()

	fun loginClicked(@Suppress("UNUSED_PARAMETER") unused: View) {
		if (!validateInputFields()) return

		tryAndHandleExceptionAsync {
			val result = userRepository.login(username.value ?: "", password.value ?: "")
			if (result) {
				AppViewModel.performNavigationAction(
					LoginFragmentDirections.actionLoginFragmentToCompositionListFragment()
				)
			} else {
				displayConsentErrorMessage(R.string.warning_wrong_username_password)
			}
		}
	}

	fun validateInputFields(): Boolean {
		if (username.value.isNullOrEmpty())
			usernameError.value = R.string.please_input_username.asTextProvider()
		else
			usernameError.value = null

		if (password.value.isNullOrEmpty())
			passwordError.value = R.string.please_input_password.asTextProvider()
		else
			passwordError.value = null

		return usernameError.value == null && passwordError.value == null
	}
}
package lehrbaum.de.onenightcomps.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.MutableNonNullLiveData
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.UserRepository
import lehrbaum.de.onenightcomps.fragments.LoginFragmentDirections
import lehrbaum.de.onenightcomps.inject

class LoginViewModel : ErrorViewModel() {
	val username = MutableLiveData<String>()
	val password = MutableLiveData<String>()
	val usernameValid = MutableNonNullLiveData(true)
	val passwordValid = MutableNonNullLiveData(true)

	private val userRepository: UserRepository by inject()

	init {
		username.value = ""
		password.value = ""
	}

	fun validateUsername(s: CharSequence, start: Int, before: Int, count: Int) {
		//undo invalid username marking
		if (!usernameValid.value && !username.value.isNullOrEmpty())
			usernameValid.value = true
	}

	fun validatePassword(s: CharSequence, start: Int, before: Int, count: Int) {
		//undo invalid username marking
		if (!passwordValid.value && !password.value.isNullOrEmpty())
			passwordValid.value = true
	}

	fun loginClicked(unused: View) {
		//TODO show dialog or something
		if (username.value.isNullOrEmpty()) {
			usernameValid.value = false
			return
		}
		if (password.value.isNullOrEmpty()) {
			passwordValid.value = false
			return
		}

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
}

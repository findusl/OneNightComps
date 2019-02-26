package lehrbaum.de.onenightcomps.viewmodel

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputLayout
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.UserRepository
import lehrbaum.de.onenightcomps.fragments.LoginFragmentDirections
import lehrbaum.de.onenightcomps.inject

class LoginViewModel : ErrorViewModel() {
	val username = MutableLiveData<String>()
	val password = MutableLiveData<String>()

	val name = "Hi"

	private val userRepository: UserRepository by inject()

	init {
		username.value = ""
		password.value = ""
	}

	fun loginClicked(unused: View) {
		if (username.value.isNullOrEmpty())
			return

		if (password.value.isNullOrEmpty())
			return

		tryAndHandleExceptionAsync {
			val result = userRepository.login(username.value!!, password.value!!)
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
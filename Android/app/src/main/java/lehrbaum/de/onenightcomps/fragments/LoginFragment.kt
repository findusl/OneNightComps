package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import lehrbaum.de.onenightcomps.databinding.LoginFragmentBinding
import lehrbaum.de.onenightcomps.viewmodel.LoginViewModel

class LoginFragment : ErrorHandlingFragment<LoginViewModel>() {

	override fun onCreateViewModel(): LoginViewModel {
		return ViewModelProviders.of(this).get(LoginViewModel::class.java)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = LoginFragmentBinding.inflate(inflater, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = this
		return binding.root
	}
}

package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import lehrbaum.de.onenightcomps.databinding.FragmentLoginBinding
import lehrbaum.de.onenightcomps.viewmodel.LoginViewModel

class LoginFragment : ErrorHandlingFragment<LoginViewModel>() {

	override fun onCreateViewModel(): LoginViewModel {
		return ViewModelProvider(this).get(LoginViewModel::class.java)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = FragmentLoginBinding.inflate(inflater, container, false)
		binding.viewModel = viewModel
		binding.lifecycleOwner = this
		return binding.root
	}
}

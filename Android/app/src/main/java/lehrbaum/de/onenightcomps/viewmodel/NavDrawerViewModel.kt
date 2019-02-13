package lehrbaum.de.onenightcomps.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.UserRepository
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.User

typealias ItemVisibilityToggler = ((Int, Boolean) -> Unit)

class NavDrawerViewModel: ViewModel() {
	var userDisplayName : MutableLiveData<String> = MutableLiveData()
	var userVisible : MutableLiveData<Int> = MutableLiveData()
	private val userRepository: UserRepository by inject()
	private var itemVisibilityToggler: ItemVisibilityToggler? = null

	init {
		userRepository.addOnUserChangedListener {
			onUserChanged(it)
		}
		onUserChanged(userRepository.currentUser)
	}

	// I don't like the naming of this. Any suggestions welcome. The idea is, that menu is a view
	// class, so it shouldn't be used in the viewModel directly. Therefore I registerAsync this sort of
	// callback which allows the viewmodel to toggle the visibility of menu items.
	fun initWithItemVisibilityToggler(itemVisibilityToggler: ItemVisibilityToggler) {
		this.itemVisibilityToggler = itemVisibilityToggler
		onUserChanged(userRepository.currentUser)
	}

	private fun onUserChanged(user: User?) {
		if(user != null) {
			userVisible.value = View.VISIBLE
			userDisplayName.value = user.username
		} else
			userVisible.value = View.GONE
		itemVisibilityToggler?.invoke(R.id.loginFragment, user == null)
		itemVisibilityToggler?.invoke(R.id.logoutAction, user != null)
	}

	fun onLogoutClicked() {
		userRepository.logout()
	}
}
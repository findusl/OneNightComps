package lehrbaum.de.onenightcomps.viewmodel

import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bmw.connride.ui.viewmodel.DelegatingViewModel
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.UserRepository
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.User

class NavDrawerViewModel : DelegatingViewModel<NavDrawerViewModel.Delegate>() {
	var userDisplayName: MutableLiveData<String> = MutableLiveData()
	var userVisible: MutableLiveData<Int> = MutableLiveData()
	private val userRepository: UserRepository by inject()

	init {
		userRepository.addOnUserChangedListener {
			onUserChanged(it)
		}
		onUserChanged(userRepository.currentUser)
	}

	override fun setDelegate(delegate: Delegate, owner: LifecycleOwner) {
		super.setDelegate(delegate, owner)
		onUserChanged(userRepository.currentUser)
	}

	private fun onUserChanged(user: User?) {
		if (user != null) {
			userVisible.value = View.VISIBLE
			userDisplayName.value = user.username
		} else
			userVisible.value = View.GONE
		delegate?.setMenuItemVisibility(R.id.loginFragment, user == null)
		delegate?.setMenuItemVisibility(R.id.logoutAction, user != null)
	}

	fun onLogoutClicked() {
		userRepository.logout()
	}

	interface Delegate {
		fun setMenuItemVisibility(id: Int, isVisible: Boolean)
	}
}

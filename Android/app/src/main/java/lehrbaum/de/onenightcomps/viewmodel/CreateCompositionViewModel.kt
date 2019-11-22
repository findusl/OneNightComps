package lehrbaum.de.onenightcomps.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.CompositionRepository
import lehrbaum.de.onenightcomps.dataaccess.RoleRepository
import lehrbaum.de.onenightcomps.fragments.CompositionCreateFragmentDirections
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.Composition
import lehrbaum.de.onenightcomps.model.GameRole
import lehrbaum.de.onenightcomps.model.MinimumCardCount

private const val TAG = "CreateCompositionVM"

class CreateCompositionViewModel : GenericErrorViewModel<CreateCompositionViewModel.Delegate>() {
	val checkableGridViewModelLiveData = MutableLiveData<CheckableGridViewModel<GameRole>>()
	val titleText = MutableLiveData<String>()
	val descriptionText = MutableLiveData<String>()
	private val compositionRepository: CompositionRepository by inject()
	private val roleRepository: RoleRepository by inject()

	init {
		requestRoles()
	}

	private fun requestRoles() {
		tryAndHandleExceptionAsync {
			val roles = roleRepository.getRolesAsync().await()
			setRoles(roles)
		}
	}

	private fun setRoles(roles: Array<GameRole>) {
		val gridViewModel = CheckableGridViewModel(roles, GameRole::name, this::onGameRoleLongClick)
		checkableGridViewModelLiveData.value = gridViewModel
	}

	private fun onGameRoleLongClick(role: GameRole) {
		delegate?.showInfoDialog(role.name, role.description)
	}

	fun onCompletedOptionSelected() {
		if (checkableGridViewModelLiveData.value == null) {
			Log.w(TAG, "The checkableGridViewModel is null, probably the roles haven't loaded yet.")
			displayConsentErrorMessage(R.string.warning_wait_for_roles_loading)
			return
		}

		val roles = (checkableGridViewModelLiveData.value ?: return).getSelectedItems()
		if (roles.size < MinimumCardCount) {
			Log.w(TAG, "Not enough roles selected, only " + roles.size)
			displayConsentErrorMessage(R.string.warning_not_enough_roles_selected, MinimumCardCount)
			return
		}

		createComposition(roles)
	}

	private fun createComposition(roles: List<GameRole>) {
		val comp = Composition(
			-1, titleText.value ?: "",
			descriptionText.value ?: "", roles = roles.toTypedArray()
		)
		tryAndHandleExceptionAsync(showLoading = true) {
			val result = compositionRepository.createCompositionAsync(comp).await()
			if (result != -1) {
				AppViewModel.performNavigationAction(
					CompositionCreateFragmentDirections
						.actionCreateCompositionFragmentToCompositionListFragment()
				)
			} else {
				Log.wtf(TAG, "No exception but couldn't create composition: $comp")
				//this shouldn't happen, it should have thrown an exception.
				displayConsentErrorMessage(R.string.error_unexpected)
			}
		}
	}

	interface Delegate : GenericErrorViewModel.Delegate {
		fun showInfoDialog(name: String, message: String)
	}
}
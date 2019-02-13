package lehrbaum.de.onenightcomps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavDirections
import lehrbaum.de.onenightcomps.fragments.CompositionListFragmentDirections
import lehrbaum.de.onenightcomps.model.Composition

/**
 * This is the ViewModel of the whole App for anything that needs to be controlled cross-fragment.
 * You could also see it as the ViewModel of the MainActivity since this is a single Activity application.
 * However it is not actually an Implementation of androidx.lifecycle.ViewModel but a static class.
 * This is easier to use but the class needs to be extra careful not to be a memory leak by referencing
 * the view even as a listener. So the reference to Resolution for example might be a problem. Should
 * think about it. FIXME
 */
object AppViewModel {
	val actionToPerform = MutableLiveData<NavDirections>()

	fun performNavigationAction(action: NavDirections) {
		actionToPerform.value = action
	}

	//Should I let the calling class directly set the value of the live data? I did a bit of encapsulation.
	private var _selectedComposition: MutableLiveData<Composition> = MutableLiveData()

	val selectedComposition: LiveData<Composition>
		get() = _selectedComposition

	fun onCompositionSelected(selectedEntry: Composition) {
		_selectedComposition.value = selectedEntry
		actionToPerform.value = CompositionListFragmentDirections
			.actionCompositionListFragmentToCompositionDetailFragment()
	}
}
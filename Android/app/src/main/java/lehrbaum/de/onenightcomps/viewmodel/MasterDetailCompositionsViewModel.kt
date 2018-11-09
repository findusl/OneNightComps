package lehrbaum.de.onenightcomps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lehrbaum.de.onenightcomps.model.Composition

class MasterDetailCompositionsViewModel : ViewModel() {
	//Should I let the calling class directly set the value of the live data? I did a bit of encapsulation...
	private var _selectedComposition: MutableLiveData<Composition> = MutableLiveData()

	val selectedComposition: LiveData<Composition>
		get() = _selectedComposition

	fun onCompositionSelected(selectedEntry: Composition) {
		_selectedComposition.value = selectedEntry
	}
}
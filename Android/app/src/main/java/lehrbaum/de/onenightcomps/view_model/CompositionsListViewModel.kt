package lehrbaum.de.onenightcomps.view_model

import androidx.core.util.Consumer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lehrbaum.de.onenightcomps.data_access.CompositionRepository
import lehrbaum.de.onenightcomps.data_access.Resolution
import lehrbaum.de.onenightcomps.model.Composition

class CompositionsListViewModel : ViewModel() {

    private val compositionRepository = CompositionRepository

	private lateinit var compositions: MutableLiveData<Array<Composition>>

	fun getCompositions(): LiveData<Array<Composition>> {
		if (!::compositions.isInitialized) {
			compositions = MutableLiveData()
			loadCompositions()
		}
		return compositions
	}

	private fun loadCompositions() {
		compositionRepository.getCompositions(
			Consumer { compositions -> this.compositions.value = compositions; },
			ResolutionProvider.resolution)
	}
}
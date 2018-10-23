package lehrbaum.de.onenightcomps.view_models

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import lehrbaum.de.onenightcomps.data_access.CompositionRepository
import lehrbaum.de.onenightcomps.model.Composition

class CompositionsListViewModel : ViewModel() {
    val compositionRepository = CompositionRepository;
    val compositionList: LiveData<List<Composition>> by lazy {
        MutableLiveData<List<Composition>>()
    }
}
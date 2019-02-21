package lehrbaum.de.onenightcomps.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import lehrbaum.de.onenightcomps.dataaccess.CompositionRepository
import lehrbaum.de.onenightcomps.inject
import lehrbaum.de.onenightcomps.model.Composition
import java.util.*

class CompositionsListViewModel : ErrorViewModel(), SwipeRefreshLayout.OnRefreshListener {
	private lateinit var compositions: MutableLiveData<Array<Composition>>

	private val compositionRepository: CompositionRepository by inject()

	fun getCompositions(): LiveData<Array<Composition>> {
		if (!::compositions.isInitialized) {
			compositions = MutableLiveData()
			loadCompositions()
		}
		return compositions
	}

	private fun loadCompositions() {
		tryAndHandleExceptionAsync(showLoading = true) {
			val comps = compositionRepository.getCompositionsAsync().await()
			Arrays.sort(comps) { o1, o2 -> o1.roles.size.compareTo(o2.roles.size) }
			compositions.value = comps
		}
	}

	override fun onRefresh() {
		loadCompositions()
	}
}
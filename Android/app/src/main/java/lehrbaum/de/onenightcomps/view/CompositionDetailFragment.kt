package lehrbaum.de.onenightcomps.view

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.composition_detail.view.*
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.model.Composition
import lehrbaum.de.onenightcomps.view_model.MasterDetailCompositionsViewModel

class CompositionDetailFragment : Fragment() {

	private lateinit var viewModel : MasterDetailCompositionsViewModel

	override fun onAttach(context: Context?) {
		super.onAttach(context)

		if(activity != null) {
			//the master detail view model depends on the parent activity so the master fragment can use it
			viewModel = ViewModelProviders.of(activity!!)
				.get(MasterDetailCompositionsViewModel::class.java)
		}
		else {
			TODO("Figure out why there could be no activity.")
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle? ): View? {
		return inflater.inflate(R.layout.composition_detail, container, false)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)

		view.composition_detail.text = viewModel.selectedComposition.value?.name ?: ""

		viewModel.selectedComposition.observe(this,
			Observer { comp -> onSelectedCompositionChanged(comp)})
	}

	private fun onSelectedCompositionChanged(comp: Composition) {
		view?.composition_detail?.text = comp.name
	}
}

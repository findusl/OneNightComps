package lehrbaum.de.onenightcomps.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProviders
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.view_model.CompositionsListViewModel
import lehrbaum.de.onenightcomps.view_model.MasterDetailCompositionsViewModel

class CompositionFragment : Fragment() {
	private var columnCount = 1

	private lateinit var masterDetailViewModel : MasterDetailCompositionsViewModel
	private lateinit var compositionsViewModel: CompositionsListViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		compositionsViewModel = ViewModelProviders.of(this)
			.get(CompositionsListViewModel::class.java)
	}

	override fun onAttach(context: Context) {
		super.onAttach(context)
		if(activity != null) {
			//the master detail view model depends on the parent activity so the detail fragment can use it
			masterDetailViewModel = ViewModelProviders.of(activity!!)
				.get(MasterDetailCompositionsViewModel::class.java)
		}
		else {
			TODO("Figure out why there could be no activity.")
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?): View?
	{
		val view = inflater.inflate(R.layout.fragment_composition_list, container, false)

		// Set the adapter
		if (view is RecyclerView) {
			with(view) {
				layoutManager = when {
					columnCount <= 1 -> LinearLayoutManager(context)
					else -> GridLayoutManager(context, columnCount)
				}
				adapter = CompositionRecyclerViewAdapter(compositionsViewModel, masterDetailViewModel)
			}
		}
		return view
	}
}
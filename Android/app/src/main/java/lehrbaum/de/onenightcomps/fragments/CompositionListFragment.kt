package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.databinding.FragmentCompositionListBinding
import lehrbaum.de.onenightcomps.model.Composition
import lehrbaum.de.onenightcomps.view.CompositionRecyclerViewAdapter
import lehrbaum.de.onenightcomps.viewmodel.AppViewModel
import lehrbaum.de.onenightcomps.viewmodel.CompositionsListViewModel

class CompositionListFragment : ErrorHandlingFragment<CompositionsListViewModel>() {

	private val appViewModel = AppViewModel

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateViewModel(): CompositionsListViewModel {
		return ViewModelProvider(this).get(CompositionsListViewModel::class.java)
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val listBinding =
			FragmentCompositionListBinding.inflate(inflater, container, false)
		listBinding.viewModel = viewModel
		// Set the adapter
		setUpRecyclerView(listBinding.list)
		setUpSwipeRefreshLayout(listBinding)
		return listBinding.root
	}

	private fun setUpSwipeRefreshLayout(listBinding: FragmentCompositionListBinding) {
		viewModel.isLoading.observe(viewLifecycleOwner, listBinding.swipeRefreshLayout::setRefreshing)
		listBinding.swipeRefreshLayout.setOnRefreshListener(viewModel)
	}

	private fun setUpRecyclerView(view: RecyclerView) {
		view.layoutManager = LinearLayoutManager(context)
		view.adapter = CompositionRecyclerViewAdapter(viewModel.getCompositions(), ::onItemSelected)
		// would love to do the observing in the CompositionRecyclerViewAdapter,
		// but it doesn't have a lifecycle.
		viewModel.getCompositions()
			.observe(viewLifecycleOwner) { view.adapter?.notifyDataSetChanged() }
	}

	private fun onItemSelected(comp: Composition) {
		appViewModel.onCompositionSelected(comp)
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.composition_list_options, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		// TODO react to search for text option
		// also move to viewmodel and pre-create bottom sheet?
		when (item.itemId) {
			R.id.filter_menu_option -> {
				val bottomSheet = FilterListBottomDialogFragment()
				parentFragmentManager.let { bottomSheet.show(it, null) }
			}
		}
		return super.onOptionsItemSelected(item)
	}
}
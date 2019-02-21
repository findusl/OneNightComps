package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProviders
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.databinding.CompositionCreateFragmentBinding
import lehrbaum.de.onenightcomps.observe
import lehrbaum.de.onenightcomps.viewmodel.CreateCompositionViewModel

class CompositionCreateFragment : ErrorHandlingFragment<CreateCompositionViewModel>() {

	override fun onCreateViewModel(): CreateCompositionViewModel {
		val viewModel = ViewModelProviders.of(this)
			.get(CreateCompositionViewModel::class.java)
		viewModel.dialogLiveEvent.showDialogOnChange(this)
		return viewModel
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding =
			CompositionCreateFragmentBinding.inflate(inflater, container, false)
		setBindings(binding)

		setHasOptionsMenu(true)
		return binding.root
	}

	private fun setBindings(binding: CompositionCreateFragmentBinding) {
		binding.lifecycleOwner = this
		binding.lifecycleOwnerVar = this
		binding.viewModel = viewModel
	}

	override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater?.inflate(R.menu.create_composition_options, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem?): Boolean {
		if (item != null && item.itemId == R.id.create_menu_option) {
			viewModel.onCompletedOptionSelected()
		}
		return super.onOptionsItemSelected(item)
	}
}

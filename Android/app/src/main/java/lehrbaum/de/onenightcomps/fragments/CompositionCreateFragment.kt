package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import android.view.*
import androidx.lifecycle.ViewModelProviders
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.databinding.FragmentCompositionCreateBinding
import lehrbaum.de.onenightcomps.viewmodel.CreateCompositionViewModel

class CompositionCreateFragment : ErrorHandlingFragment<CreateCompositionViewModel>(),
                                  CreateCompositionViewModel.Delegate {

	override fun onCreateViewModel(): CreateCompositionViewModel {
		val viewModel = ViewModelProviders.of(this)
			.get(CreateCompositionViewModel::class.java)

		viewModel.setDelegate(this, this)
		return viewModel
	}

	override fun showInfoDialog(name: String, message: String) {
		showDialog(DialogViewModel(name, message, DialogType.INFO_DIALOG))
	}

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding =
			FragmentCompositionCreateBinding.inflate(inflater, container, false)
		setBindings(binding)

		setHasOptionsMenu(true)
		return binding.root
	}

	private fun setBindings(binding: FragmentCompositionCreateBinding) {
		binding.lifecycleOwner = this
		binding.lifecycleOwnerVar = this
		binding.viewModel = viewModel
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.create_composition_options, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem): Boolean {
		if (item.itemId == R.id.create_menu_option) {
			viewModel.onCompletedOptionSelected()
		}
		return super.onOptionsItemSelected(item)
	}
}

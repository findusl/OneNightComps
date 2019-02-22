package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import lehrbaum.de.onenightcomps.databinding.FragmentCompositionDetailBinding
import lehrbaum.de.onenightcomps.viewmodel.AppViewModel

class CompositionDetailFragment : Fragment() {

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		val binding = FragmentCompositionDetailBinding.inflate(inflater, container, false)
		binding.lifecycleOwner = this
		binding.composition = AppViewModel.selectedComposition.value
		binding.rolesView.roleClickListener = {
			showDialog(DialogViewModel(it.name, it.description, DialogType.INFO_DIALOG))
		}

		return binding.root
	}
}

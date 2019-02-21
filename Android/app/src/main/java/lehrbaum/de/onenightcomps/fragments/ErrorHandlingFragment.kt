package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import lehrbaum.de.onenightcomps.observe
import lehrbaum.de.onenightcomps.viewmodel.ErrorViewModel
import lehrbaum.de.onenightcomps.viewmodel.TextProvider


abstract class ErrorHandlingFragment<ViewModelType : ErrorViewModel> : Fragment() {
	protected lateinit var viewModel: ViewModelType
		private set

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel = onCreateViewModel()
		viewModel.disappearingErrorLiveEvent.observe(
			this,
			this::onDisappearingErrorMessageChanged
		)
		viewModel.consentErrorLiveEvent.observe(
			this,
			this::onConsentErrorMessageChanged
		)
	}

	private fun onDisappearingErrorMessageChanged(textProvider: TextProvider) {
		if (view != null && context != null)
			Snackbar.make(view!!, textProvider(context!!), Snackbar.LENGTH_LONG).show()
	}

	private fun onConsentErrorMessageChanged(textProvider: TextProvider) {
		showDialog(DialogViewModel("A problem occurred",
								   textProvider(context!!),
								   DialogType.ALERT_DIALOG))
	}

	// could make an options menu option that shows a sticking error.
	// android allows to keep the reference and set visible and invisible as needed.

	/**
	 * Called during onCreate.
	 */
	abstract fun onCreateViewModel(): ViewModelType
}
package lehrbaum.de.onenightcomps.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar
import lehrbaum.de.onenightcomps.TextProvider
import lehrbaum.de.onenightcomps.viewmodel.ErrorViewModel
import lehrbaum.de.onenightcomps.viewmodel.GenericErrorViewModel


abstract class ErrorHandlingFragment<ViewModelType : ErrorViewModel> :
	GenericErrorHandlingFragment<ViewModelType>() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel.setDelegate(this, this)
	}
}

abstract class GenericErrorHandlingFragment<ViewModelType : GenericErrorViewModel<*>> :
		Fragment(), GenericErrorViewModel.Delegate {

	protected lateinit var viewModel: ViewModelType
		private set

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		viewModel = onCreateViewModel()
	}

	override fun showDisappearingError(message: TextProvider) {
		val view = view ?: return
		val context = context ?: return
		Snackbar.make(view, message(context), Snackbar.LENGTH_LONG).show()
	}

	override fun showConsentError(message: TextProvider) {
		val context = context ?: return
		showDialog(
			DialogViewModel("A problem occurred", message(context), DialogType.ALERT_DIALOG)
		)
	}

	// could make an options menu option that shows a sticking error.
	// android allows to keep the reference and set visible and invisible as needed.

	/**
	 * Called during onCreate.
	 */
	abstract fun onCreateViewModel(): ViewModelType
}
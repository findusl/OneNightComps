package lehrbaum.de.onenightcomps.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.lifecycle.MutableLiveData
import com.bmw.connride.ui.viewmodel.DelegatingViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.DataAccessException
import lehrbaum.de.onenightcomps.dataaccess.NetworkUnavailableException
import lehrbaum.de.onenightcomps.dataaccess.ResourceNotFoundException
import lehrbaum.de.onenightcomps.map
import lehrbaum.de.onenightcomps.view.TextProvider
import lehrbaum.de.onenightcomps.view.asTextProvider

private const val TAG = "ErrorViewModel"

open class ErrorViewModel : GenericErrorViewModel<GenericErrorViewModel.Delegate>()

open class GenericErrorViewModel<DelegateType : GenericErrorViewModel.Delegate> :
		DelegatingViewModel<DelegateType>() {
	//TODO check internet connection here and keep it shown

	val isLoading = MutableLiveData<Boolean>()
	val loadingIndicatorVisibility = isLoading.map<Boolean, Int>(::isVisibleToVisibility)

	init {
		isLoading.value = false
	}

	internal fun tryAndHandleExceptionAsync(
		showLoading: Boolean = false,
		block: suspend () -> Unit
	) {
		GlobalScope.launch(Dispatchers.Main) {
			try {
				if (showLoading) isLoading.value = true
				block()
				if (showLoading) isLoading.value = false
			} catch (e: DataAccessException) {
				handleDataAccessException(e)
			} finally {
				if (showLoading) isLoading.value = false
			}
		}
	}

	internal fun <ReturnType> tryAndHandleException(block: () -> ReturnType): ReturnType? {
		return try {
			block()
		} catch (e: DataAccessException) {
			handleDataAccessException(e)
			null
		}
	}

	private fun handleDataAccessException(e: DataAccessException) {
		Log.w(TAG, "Got $e in tryAndHandle in ErrorViewModel", e)
		when (e) {
			is NetworkUnavailableException ->
				displayDisappearingErrorMessage(R.string.warning_no_internet_connection)
			is ResourceNotFoundException ->
				displayConsentErrorMessage(R.string.error_resource_not_found)
			else -> displayConsentErrorMessage(R.string.error_unknown, e.toString())
		}
	}

	/**
	 * @see Context.getString
	 */
	private fun displayDisappearingErrorMessage(
		@StringRes errorMessageStringResource: Int,
		vararg formatArgs: Any?
	) {
		delegate?.showDisappearingError(errorMessageStringResource.asTextProvider(*formatArgs))
	}

	/**
	 * @see Context.getString
	 */
	protected fun displayConsentErrorMessage(
		@StringRes errorMessageStringResource: Int,
		vararg formatArgs: Any
	) {
		delegate?.showConsentError(errorMessageStringResource.asTextProvider(*formatArgs))
	}

	interface Delegate {
		fun showDisappearingError(message: TextProvider)
		fun showConsentError(message: TextProvider)
	}
}

private fun isVisibleToVisibility(isVisible: Boolean): Int {
	return if (isVisible) View.VISIBLE else View.GONE
}
package lehrbaum.de.onenightcomps.viewmodel

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import lehrbaum.de.onenightcomps.R
import lehrbaum.de.onenightcomps.dataaccess.DataAccessException
import lehrbaum.de.onenightcomps.dataaccess.NetworkUnavailableException
import lehrbaum.de.onenightcomps.dataaccess.ResourceNotFoundException
import lehrbaum.de.onenightcomps.map

typealias TextProvider = (c: Context) -> String

private const val TAG = "ErrorViewModel"

open class ErrorViewModel : ViewModel() {
	//TODO check internet connection here and keep it shown
	private val _disappearingErrorMessage = MutableLiveData<TextProvider>()

	val disappearingErrorMessage : LiveData<TextProvider>
		get() = _disappearingErrorMessage

	private val _consentErrorMessage = MutableLiveData<TextProvider>()

	val consentErrorMessage : LiveData<TextProvider>
		get() = _consentErrorMessage

	val isLoading = MutableLiveData<Boolean>()
	val loadingIndicatorVisibility = isLoading.map<Boolean, Int>(::isVisibleToVisibility)

	init {
		isLoading.value = false
	}

	internal fun tryAndHandleExceptionAsync(showLoading: Boolean = false,
								   			block: suspend () -> Unit) {
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

	internal fun <ReturnType> tryAndHandleException(block: () -> ReturnType) : ReturnType? {
		try {
			return block()
		} catch (e: DataAccessException) {
			handleDataAccessException(e)
			return null
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
	protected fun displayDisappearingErrorMessage(errorMessageStringResource: Int, vararg formatArgs: Any) {
		_disappearingErrorMessage.value = {it.getString(errorMessageStringResource, *formatArgs)}
	}

	/**
	 * @see Context.getString
	 */
	protected fun displayConsentErrorMessage(errorMessageStringResource: Int, vararg formatArgs: Any) {
		_consentErrorMessage.value = {it.getString(errorMessageStringResource, *formatArgs)}
	}
}

private fun isVisibleToVisibility(isVisible: Boolean): Int {
	return if (isVisible) View.VISIBLE else View.GONE
}
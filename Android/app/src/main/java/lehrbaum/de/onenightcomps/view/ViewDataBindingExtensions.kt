package lehrbaum.de.onenightcomps.view

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object ViewDataBindingExtensions {

	@JvmStatic
	@BindingAdapter("error")
	fun bindError(textInputLayout: TextInputLayout, stringResource: TextProvider?) {
		val context = textInputLayout.context ?: return
		textInputLayout.error =
			if (stringResource == null) null
			else stringResource(context)
	}
}

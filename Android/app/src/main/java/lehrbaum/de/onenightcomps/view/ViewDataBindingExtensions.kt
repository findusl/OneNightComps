package lehrbaum.de.onenightcomps.view

import android.view.View
import android.widget.EditText
import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object ViewDataBindingExtensions {
	@JvmStatic
	@BindingAdapter("errorText")
	fun bindErrorText(textInputLayout: TextInputLayout, errorText: String) {
		textInputLayout.error = errorText
	}

	@JvmStatic
	@BindingAdapter("onFocus")
	fun bindFocusChange(editText: EditText, onFocusChangeListener: View.OnFocusChangeListener) {
		if (editText.onFocusChangeListener == null) {
			editText.onFocusChangeListener = onFocusChangeListener
		}
	}
}

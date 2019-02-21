package lehrbaum.de.onenightcomps.fragments

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import lehrbaum.de.onenightcomps.LiveEvent
import lehrbaum.de.onenightcomps.observe

enum class DialogType {
	ALERT_DIALOG,
	INFO_DIALOG
}

data class DialogViewModel(val title: String, val description: String, val dialogType: DialogType)

fun LiveEvent<DialogViewModel>.showDialogOnChange(fragment: Fragment) {
	observe(fragment) {
		if (it != null)
			fragment.showDialog(it)
	}
}

fun Fragment.showDialog(dialogViewModel: DialogViewModel) {
	if (context == null) return
	AlertDialog.Builder(context!!)
		.setTitle(dialogViewModel.title)
		.setMessage(dialogViewModel.description)
		.setPositiveButton(android.R.string.ok, null)
		.setIcon(dialogViewModel.dialogType.toDialogDrawable())
		.show()
}

private fun DialogType.toDialogDrawable(): Int =
	when (this) {
		DialogType.ALERT_DIALOG -> android.R.drawable.ic_dialog_alert
		DialogType.INFO_DIALOG -> android.R.drawable.ic_dialog_info
	}

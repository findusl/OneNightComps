package lehrbaum.de.onenightcomps.fragments

import android.content.Context
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment

enum class DialogType {
	ALERT_DIALOG,
	INFO_DIALOG
}

data class DialogViewModel(val title: String, val description: String, val dialogType: DialogType) {
	constructor(titleResource: Int,
				descriptionResource: Int,
				dialogType: DialogType,
				context: Context)
			: this(context.getString(titleResource),
				   context.getString(descriptionResource),
				   dialogType)
}

fun Fragment.showDialog(dialogViewModel: DialogViewModel) {
	val context = context ?: return
	AlertDialog.Builder(context)
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

package lehrbaum.de.onenightcomps.viewmodel

import android.view.View
import androidx.lifecycle.MutableLiveData

typealias LongClickItemListener<ItemType> = (ItemType) -> Unit

class CheckableGridViewModel<ItemType>(
	items: Array<ItemType>,
	val itemStringConverter: (item: ItemType) -> String = { it.toString() },
	longClickListener: LongClickItemListener<ItemType>? = null
) {
	val viewModels: Array<SimpleCheckableListItemViewModel<ItemType>> =
		items.map { item ->
			val viewModel = SimpleCheckableListItemViewModel(item, itemStringConverter(item))
			viewModel.longClickListener = longClickListener
			viewModel
		}.toTypedArray()

	fun getSelectedItems(): List<ItemType> {
		return viewModels.filter { it.checked.value!! }.map { it.item }
	}
}

class SimpleCheckableListItemViewModel<ItemType>(val item: ItemType, text: String) :
	View.OnLongClickListener, View.OnClickListener {
	val checked: MutableLiveData<Boolean> = MutableLiveData()
	val text: MutableLiveData<String> = MutableLiveData()
	var longClickListener: LongClickItemListener<ItemType>? = null

	init {
		checked.value = false
		this.text.value = text
	}

	override fun onClick(v: View) {
		checked.value = !checked.value!!
	}

	override fun onLongClick(v: View?): Boolean {
		if (longClickListener != null) {
			longClickListener?.invoke(item)
			return true
		}
		return false
	}
}
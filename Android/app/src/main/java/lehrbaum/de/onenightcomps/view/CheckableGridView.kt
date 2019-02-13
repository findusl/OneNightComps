package lehrbaum.de.onenightcomps.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lehrbaum.de.onenightcomps.databinding.SimpleCheckableListItemBinding

class CheckableGridView<ItemType> @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

	private var _viewModel: CheckableGridViewModel<ItemType>? = null

	var viewModel: CheckableGridViewModel<ItemType>?
		get() = _viewModel
		set(value) {
			_viewModel = value
			generateAdapter()
		}

	private var _lifecycleOwner: LifecycleOwner? = null

	init {
		layoutManager = GridLayoutManager(context, 2)
	}

	var lifecycleOwner: LifecycleOwner?
		get() = _lifecycleOwner
		set(value) {
			_lifecycleOwner = value
			generateAdapter()//TODO this is not a nice solution
		}

	private fun generateAdapter() {
		//generate viewModel
		if(lifecycleOwner != null && viewModel != null) {
			adapter = CheckableArrayAdapter(context, lifecycleOwner!!, viewModel!!.viewModels)
		}
	}
}

class CheckableGridViewModel<ItemType>(
		items: Array<ItemType>,
		val itemStringConverter: (item:ItemType) -> String = {it.toString()}) {
	val viewModels:Array<SimpleCheckableListItemViewModel<ItemType>> =
			items.map { item -> SimpleCheckableListItemViewModel(item, itemStringConverter(item)) }.toTypedArray()
	fun getSelectedItems(): List<ItemType> {
		return viewModels.filter { it.checked.value!! }.map { it.item }
	}
}

class CheckableArrayAdapter<ItemType>(context: Context,
							private val lifecycleOwner: LifecycleOwner,
							private val viewModels: Array<SimpleCheckableListItemViewModel<ItemType>>)
	: RecyclerView.Adapter<SimpleCheckableViewHolder>() {
	val inflater: LayoutInflater = LayoutInflater.from(context)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleCheckableViewHolder {
		val binding =
			SimpleCheckableListItemBinding.inflate(inflater, parent, false)
		binding.lifecycleOwner = lifecycleOwner
		return SimpleCheckableViewHolder(binding)
	}

	override fun getItemCount(): Int {
		return this.viewModels.size
	}

	override fun onBindViewHolder(holder: SimpleCheckableViewHolder, position: Int) {
		holder.binding.viewModel = viewModels[position]
	}
}

class SimpleCheckableViewHolder(
	val binding: SimpleCheckableListItemBinding): SimpleViewHolder(binding.textView)

class SimpleCheckableListItemViewModel<ItemType>(val item: ItemType, text: String) {
	val checked : MutableLiveData<Boolean> = MutableLiveData()
	val text : MutableLiveData<String> = MutableLiveData()

	init {
		checked.value = false
		this.text.value = text
	}

	fun onClick(v: View) {
		checked.value = !checked.value!!
	}
}
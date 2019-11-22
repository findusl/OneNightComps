package lehrbaum.de.onenightcomps.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lehrbaum.de.onenightcomps.databinding.SimpleCheckableListItemBinding
import lehrbaum.de.onenightcomps.viewmodel.CheckableGridViewModel
import lehrbaum.de.onenightcomps.viewmodel.SimpleCheckableListItemViewModel

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
			generateAdapter()//is this the nicest solution? gotta wait for all values to be set.
		}

	private fun generateAdapter() {
		val lifecycleOwner = lifecycleOwner ?: return
		val viewModel = viewModel ?: return
		adapter = CheckableArrayAdapter(context, lifecycleOwner, viewModel.viewModels)
	}
}

class CheckableArrayAdapter<ItemType>(
	context: Context,
	private val lifecycleOwner: LifecycleOwner,
	private val viewModels: Array<SimpleCheckableListItemViewModel<ItemType>>
) : RecyclerView.Adapter<SimpleCheckableViewHolder>() {
	private val inflater: LayoutInflater = LayoutInflater.from(context)

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
	val binding: SimpleCheckableListItemBinding
) : SimpleViewHolder(binding.textView)
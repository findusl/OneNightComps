package lehrbaum.de.onenightcomps.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import lehrbaum.de.onenightcomps.model.GameRole

typealias RoleClickListener = (GameRole) -> Unit

open class RoleGridView @JvmOverloads constructor(
	context: Context,
	attrs: AttributeSet? = null,
	defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

	private var _itemSource: Array<GameRole>? = null
	var itemSource: Array<GameRole>?
		get() = _itemSource
		set(value) {
			_itemSource = value
			if (value != null)
				generateAdapter()
		}

	private var _roleClickListener: RoleClickListener? = null
	var roleClickListener: RoleClickListener?
		get() = _roleClickListener
		set(value) {
			_roleClickListener = value
			if (value != null)
				generateAdapter()
		}

	init {
		layoutManager = GridLayoutManager(context, 2)
	}

	protected open fun generateAdapter() {
		if(itemSource != null && roleClickListener != null)
			adapter = ArrayAdapter(context, itemSource ?: arrayOf(), GameRole::name, roleClickListener!!)
	}


}

class ArrayAdapter<ItemType>(
	context: Context,
	private val items: Array<ItemType>,
	private val converter: ((ItemType) -> String),
	private val clickListener: (ItemType) -> Unit
) : RecyclerView.Adapter<SimpleViewHolder>() {
	val inflater: LayoutInflater = LayoutInflater.from(context)

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
		val viewHolder = SimpleViewHolder(
			inflater.inflate(
				android.R.layout.simple_list_item_1, parent,
				false
			) as TextView
		)
		viewHolder.textView.setOnClickListener {
			clickListener(items[viewHolder.adapterPosition])
		}
		return viewHolder
	}

	override fun getItemCount(): Int {
		return this.items.size
	}

	override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
		holder.textView.text = converter(items[position])
	}
}

open class SimpleViewHolder(val textView: TextView) : RecyclerView.ViewHolder(textView)
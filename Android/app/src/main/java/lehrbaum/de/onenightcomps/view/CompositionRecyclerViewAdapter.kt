package lehrbaum.de.onenightcomps.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import lehrbaum.de.onenightcomps.R

import kotlinx.android.synthetic.main.fragment_compositions_list.view.*
import lehrbaum.de.onenightcomps.model.Composition

class CompositionRecyclerViewAdapter(
	private val compositionsList: LiveData<Array<Composition>>,
	private val onItemSelectedListener: (c: Composition) -> Unit
) : RecyclerView.Adapter<CompositionRecyclerViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.fragment_compositions_list, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val comp = compositionsList.value?.get(position) ?: return//can't bind on not existing item
		holder.iddView.text = comp.playerCount.toString()
		holder.contentView.text = comp.name
		holder.view.tag = comp
		holder.view.setOnClickListener { onItemSelectedListener(comp) }
	}

	override fun getItemCount(): Int = compositionsList.value?.size ?: 0

	inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val iddView: TextView = view.item_number
		val contentView: TextView = view.content
	}
}

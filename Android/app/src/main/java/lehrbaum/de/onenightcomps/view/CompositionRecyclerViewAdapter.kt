package lehrbaum.de.onenightcomps.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import lehrbaum.de.onenightcomps.R

import kotlinx.android.synthetic.main.fragment_composition.view.*
import lehrbaum.de.onenightcomps.model.Composition
import lehrbaum.de.onenightcomps.view_model.CompositionsListViewModel
import lehrbaum.de.onenightcomps.view_model.MasterDetailCompositionsViewModel

class CompositionRecyclerViewAdapter(
	private val compositionsListViewModel: CompositionsListViewModel,
	private val masterDetailViewModel: MasterDetailCompositionsViewModel
	) : RecyclerView.Adapter<CompositionRecyclerViewAdapter.ViewHolder>() {

	private val compositionsList = compositionsListViewModel.getCompositions()

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val view = LayoutInflater.from(parent.context)
			.inflate(R.layout.fragment_composition, parent, false)
		return ViewHolder(view)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val comp = compositionsList.value?.get(position) ?: return//can't bind on not existing item
		holder.iddView.text = comp.id.toString()
		holder.contentView.text = comp.name
		holder.view.tag = comp
		holder.view.setOnClickListener { viewHolder ->
			masterDetailViewModel.onCompositionSelected(viewHolder.tag as Composition) }
	}

	override fun getItemCount(): Int = compositionsList.value?.size ?: 0

	inner class ViewHolder(val view: View) : RecyclerView.ViewHolder(view) {
		val iddView: TextView = view.item_number
		val contentView: TextView = view.content

		override fun toString(): String {
			return super.toString() + " '" + contentView.text + "'"
		}
	}
}

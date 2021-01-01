package lehrbaum.de.onenightcomps.view

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.LiveData
import lehrbaum.de.onenightcomps.databinding.FragmentCompositionListBinding
import lehrbaum.de.onenightcomps.databinding.FragmentCompositionsListBinding

import lehrbaum.de.onenightcomps.model.Composition

class CompositionRecyclerViewAdapter(
	private val compositionsList: LiveData<Array<Composition>>,
	private val onItemSelectedListener: (c: Composition) -> Unit
) : RecyclerView.Adapter<CompositionRecyclerViewAdapter.ViewHolder>() {

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
		val binding = FragmentCompositionsListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		return ViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ViewHolder, position: Int) {
		val comp = compositionsList.value?.get(position) ?: return // can't bind on not existing item
		holder.iddView.text = comp.playerCount.toString()
		holder.contentView.text = comp.name
		holder.itemView.tag = comp
		holder.itemView.setOnClickListener { onItemSelectedListener(comp) }
	}

	override fun getItemCount(): Int = compositionsList.value?.size ?: 0

	inner class ViewHolder(val binding: FragmentCompositionsListBinding) : RecyclerView.ViewHolder(binding.root) {
		val iddView: TextView = binding.itemNumber
		val contentView: TextView = binding.content
	}
}

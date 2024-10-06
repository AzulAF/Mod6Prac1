package com.azul.mod6prac1.ui.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.data.network.model.ItemOutsideDto
import com.azul.mod6prac1.databinding.ItemElementBinding

class ItemListAdapter(
//SE COMENTO LO SIGUIENTE PARA QUE FUNCIONARALA ACT 1
    private val items: MutableList<ItemOutsideDto>,
    private val onItemClicked: (ItemOutsideDto) -> Unit
): RecyclerView.Adapter<ItemListViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemListViewHolder {
        val binding = ItemElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemListViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemListViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClicked(item)
        }
    }

}
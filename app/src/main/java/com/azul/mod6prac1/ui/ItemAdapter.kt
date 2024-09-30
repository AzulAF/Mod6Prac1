package com.azul.mod6prac1.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.databinding.ItemElementBinding

class ItemAdapter (
    private val onItemClicked: (ItemEntity) -> Unit
): RecyclerView.Adapter<ItemViewHolder>(){
    private var items: MutableList<ItemEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val binding = ItemElementBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ItemViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val item = items[position]
        holder.bind(item)
        holder.itemView.setOnClickListener{
            onItemClicked(item)
        }
    }

    fun updateList(list: MutableList<ItemEntity>){
        items = list
        notifyDataSetChanged()
    }
}
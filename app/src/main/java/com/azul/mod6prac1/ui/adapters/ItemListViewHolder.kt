package com.azul.mod6prac1.ui.adapters

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.data.network.model.ItemOutsideDto
import com.azul.mod6prac1.databinding.ItemElementBinding
import com.bumptech.glide.Glide

class ItemListViewHolder (
    private val binding: ItemElementBinding
):RecyclerView.ViewHolder(binding.root){

    fun bind(item: ItemOutsideDto){
        binding.apply {
            tvMerch.visibility = View.INVISIBLE
            tvTable.text = item.mesa
            tvArtist.text = item.nombre
            tvCost.visibility = View.INVISIBLE
            tvTag.text = "Piso: " + item.piso
            tvMerchType.visibility = View.INVISIBLE
            tvMerchPriority.visibility = View.INVISIBLE
        }
        Glide.with(binding.root.context).load(item.imagen).into(binding.ivIcon)


    }
}
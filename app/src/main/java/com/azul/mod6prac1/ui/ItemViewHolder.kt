package com.azul.mod6prac1.ui

import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.azul.mod6prac1.R

import com.azul.mod6prac1.data.db.model.ItemEntity
import com.azul.mod6prac1.databinding.ItemElementBinding

class ItemViewHolder (
    private val binding: ItemElementBinding
): RecyclerView.ViewHolder(binding.root)  {
    fun bind(item: ItemEntity){
        binding.apply {
            tvMerch.text = item.merch
            tvTable.text = item.table.toString()
            tvArtist.text = item.name
            tvCost.text = item.cost.toString()
            tvTag.text = item.tag
            tvMerchType.text = item.type
            tvMerchPriority.text = item.merchPriority
            Log.d("SpinnerSelectionIMAGE", "Selected option: ${item.merchPriority}")
            val imageResource = when (item.merchPriority){

                "ALTA" -> R.drawable.priority1
                "MEDIA"-> R.drawable.priority2
                "BAJA" -> R.drawable.priority3
                else -> R.drawable.priority4
            }
            binding.ivIcon.setImageResource(imageResource)
        }
    }

}
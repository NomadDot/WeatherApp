package com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.adapter

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.drowsynomad.pettersonweatherapp.databinding.ItemLocationBinding

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

data class LocationVH(
    val itemBinding: ItemLocationBinding,
    val onItemClick: LocationAction
): ViewHolder(itemBinding.root) {
    fun bind(dataHolder: LocationDH) {
        with(itemBinding) {
            root.setOnClickListener {
                onItemClick.invoke(dataHolder.name)
            }
            tvLocation.text = dataHolder.name
        }
    }
}
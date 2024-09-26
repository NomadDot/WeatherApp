package com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.drowsynomad.pettersonweatherapp.databinding.ItemLocationBinding

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

typealias LocationAction = (place: String) -> Unit

class LocationAdapter(
    private val onItemClick: LocationAction
) : RecyclerView.Adapter<LocationVH>() {

    private val data = mutableListOf<LocationDH>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newValues: List<LocationDH>) {
        data.clear()
        data.addAll(newValues)
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = data.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LocationVH {
        return LocationVH(
            itemBinding = ItemLocationBinding.inflate(LayoutInflater.from(parent.context)),
            onItemClick = onItemClick
        )
    }

    override fun onBindViewHolder(holder: LocationVH, position: Int) {
        holder.bind(dataHolder = data[position])
    }
}
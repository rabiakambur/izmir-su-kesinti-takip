package com.rabiakambur.izmirsukesintitakip.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rabiakambur.izmirsukesintitakip.data.model.WaterFaultResponse
import com.rabiakambur.izmirsukesintitakip.databinding.ItemWaterFaultBinding

class WaterFaultAdapter(private val waterFault: List<WaterFaultResponse>) :
    RecyclerView.Adapter<WaterFaultAdapter.WaterFaultHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WaterFaultAdapter.WaterFaultHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemWaterFaultBinding.inflate(inflater, parent, false)
        return WaterFaultHolder(binding)
    }

    override fun getItemCount(): Int {
        return waterFault.size
    }

    override fun onBindViewHolder(holder: WaterFaultAdapter.WaterFaultHolder, position: Int) {
        holder.bind(waterFault[position])
    }

    inner class WaterFaultHolder(private val binding: ItemWaterFaultBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(model: WaterFaultResponse) {
            binding.districtName.text = model.district
            binding.date.text = model.date
            binding.explanation.text = model.explanation
            binding.neighborhood.text = model.neighborhood
        }
    }
}
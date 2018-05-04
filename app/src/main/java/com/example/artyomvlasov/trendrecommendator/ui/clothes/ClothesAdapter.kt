package com.example.artyomvlasov.trendrecommendator.ui.clothes

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.data.ClothesItem
import com.example.artyomvlasov.trendrecommendator.ui.clothes.ClothesAdapter.ViewHolder
import kotlinx.android.synthetic.main.item_clothes.view.*


class ClothesAdapter(private var items: List<ClothesItem>) : RecyclerView.Adapter<ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
            ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_clothes, parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    fun setData(clothesItems: List<ClothesItem>) {
        this.items = clothesItems
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(clothesItem: ClothesItem) {
            itemView.clothesName.text = clothesItem.name
            itemView.clothesDescription.text = clothesItem.description
        }
    }
}
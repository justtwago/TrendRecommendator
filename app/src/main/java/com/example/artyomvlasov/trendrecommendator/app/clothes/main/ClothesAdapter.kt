package com.example.artyomvlasov.trendrecommendator.app.clothes.main

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.artyomvlasov.trendrecommendator.R
import com.example.artyomvlasov.trendrecommendator.app.clothes.details.ClothesDetailsActivity
import com.example.artyomvlasov.trendrecommendator.data.ClothesItem
import com.example.artyomvlasov.trendrecommendator.app.clothes.main.ClothesAdapter.ViewHolder
import com.example.artyomvlasov.trendrecommendator.app.utils.Constants
import com.example.artyomvlasov.trendrecommendator.app.utils.extensions.setImageFromUrl
import kotlinx.android.synthetic.main.item_clothes.view.*


class ClothesAdapter(private val context: Context, private var items: List<ClothesItem>) : RecyclerView.Adapter<ViewHolder>() {

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
            itemView.clothesIcon.setImageFromUrl(clothesItem.image.sizes.thumb.url)
            itemView.setOnClickListener {
                openClothesDetails(clothesItem)
            }
        }

        private fun openClothesDetails(clothesItem: ClothesItem) {
            val intent = Intent(context, ClothesDetailsActivity::class.java)
            intent.putExtra(Constants.CLOTHES_ITEM_KEY, clothesItem)
            context.startActivity(intent)
        }
    }
}

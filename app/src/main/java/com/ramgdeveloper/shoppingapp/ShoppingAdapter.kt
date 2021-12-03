package com.ramgdeveloper.shoppingapp

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdeveloper.shoppingapp.databinding.ItemsRowBinding
import com.ramgdeveloper.shoppingapp.model.Items

 class ShoppingAdapter(var itemsList: ArrayList<Items>): ListAdapter<Items,ShoppingAdapter.MyViewHolder>(DiffUtiCallback) {

    object DiffUtiCallback: DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.itemName == newItem.itemName
        }
    }

    inner class MyViewHolder(private val binding: ItemsRowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(items: Items?) {
            Glide.with(binding.itemImageView).load(items?.itemImage).placeholder(R.drawable.ic_rolling_0_7s_128px).into(binding.itemImageView)

            binding.nameTv.text = items?.itemName
            binding.itemPrice.text = "Ksh: ${items?.itemPrice}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemsRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
        itemsList.add(items)
    }
}
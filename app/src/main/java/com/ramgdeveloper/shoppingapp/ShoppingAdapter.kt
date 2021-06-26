package com.ramgdeveloper.shoppingapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdeveloper.shoppingapp.databinding.ItemsRowBinding
import com.ramgdeveloper.shoppingapp.model.Items

class ShoppingAdapter: ListAdapter<Items,ShoppingAdapter.MyViewHolder>(DiffUtiCallback) {

    object DiffUtiCallback: DiffUtil.ItemCallback<Items>() {
        override fun areItemsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Items, newItem: Items): Boolean {
            return oldItem.itemName == newItem.itemName
        }
    }

    inner class MyViewHolder(private val binding: ItemsRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(items: Items?) {
            Glide.with(binding.itemImageView).load(items?.itemImage).placeholder(R.drawable.ic_rolling).into(binding.itemImageView)

            binding.nameTv.text = items?.itemName
            binding.itemPrice.text = items?.itemPrice
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemsRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
    }
}
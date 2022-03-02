package com.ramgdeveloper.shoppingapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ramgdeveloper.shoppingapp.R
import com.ramgdeveloper.shoppingapp.databinding.ItemsRowBinding
import com.ramgdeveloper.shoppingapp.model.ItemsEntity

 class ShoppingAdapter(private var itemsEntityList: ArrayList<ItemsEntity>): ListAdapter<ItemsEntity, ShoppingAdapter.MyViewHolder>(
     DiffUtiCallback) {

    object DiffUtiCallback: DiffUtil.ItemCallback<ItemsEntity>() {
        override fun areItemsTheSame(oldItem: ItemsEntity, newItem: ItemsEntity): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ItemsEntity, newItem: ItemsEntity): Boolean {
            return oldItem.id == newItem.id
        }
    }

    inner class MyViewHolder(private val binding: ItemsRowBinding): RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(itemsEntity: ItemsEntity?) {
            Glide.with(binding.itemImageView).load(itemsEntity?.itemImage).placeholder(R.drawable.ic_rolling_0_7s_128px).into(binding.itemImageView)

            binding.nameTv.text = itemsEntity?.itemName
            binding.itemPrice.text = "Ksh: ${itemsEntity?.itemPrice}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(ItemsRowBinding.inflate(LayoutInflater.from(parent.context),parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val items = getItem(position)
        holder.bind(items)
        itemsEntityList.add(items)
    }
}
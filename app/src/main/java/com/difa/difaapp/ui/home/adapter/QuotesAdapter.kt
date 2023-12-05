package com.difa.difaapp.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.difa.difaapp.data.local.entity.Quotes
import com.difa.difaapp.databinding.ItemSlideQuotesBinding

class QuotesAdapter : ListAdapter<Quotes, QuotesAdapter.QuotesViewHolder>(DIFF_CALLBACK){

    class QuotesViewHolder(val binding: ItemSlideQuotesBinding): RecyclerView.ViewHolder(binding.root){}

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Quotes>(){
            override fun areItemsTheSame(oldItem: Quotes, newItem: Quotes): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: Quotes, newItem: Quotes): Boolean {
                return oldItem == newItem
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QuotesViewHolder {
        val binding = ItemSlideQuotesBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return QuotesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: QuotesViewHolder, position: Int) {
        val data = getItem(position)
        holder.binding.tvQuotesItem.text = "\"${data.quotes}\""
        holder.binding.tvCreator.text = data.creator
    }
}
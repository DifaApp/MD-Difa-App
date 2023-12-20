package com.difa.difaapp.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.difa.difaapp.data.local.entity.Recommendation
import com.difa.difaapp.databinding.ItemLearnSibiBinding

class RecommendationAdapter: ListAdapter<Recommendation, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {

    class RecommendationViewHolder(val binding: ItemLearnSibiBinding):RecyclerView.ViewHolder(binding.root){

    }
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendationViewHolder {
        val binding = ItemLearnSibiBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RecommendationViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: RecommendationViewHolder,
        position: Int
    ) {
        val recommendationItem = getItem(position)
        Glide.with(holder.binding.root)
            .load(recommendationItem.urlImage)
            .centerCrop()
            .into(holder.binding.ivImageSibi)
        holder.apply {
            binding.tvChannel.text = recommendationItem.title
        }
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<Recommendation> =
            object : DiffUtil.ItemCallback<Recommendation>() {
                override fun areItemsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                    return oldItem.id == newItem.id
                }

                @SuppressLint("DiffUtilEquals")
                override fun areContentsTheSame(oldItem: Recommendation, newItem: Recommendation): Boolean {
                    return oldItem == newItem
                }
            }
    }
}
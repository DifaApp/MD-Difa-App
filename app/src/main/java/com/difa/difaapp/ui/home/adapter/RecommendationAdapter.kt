package com.difa.difaapp.ui.home.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.difa.difaapp.data.local.entity.Recommendation
import com.difa.difaapp.databinding.ItemLearnSibiBinding

class RecommendationAdapter(private val context: Context): ListAdapter<Recommendation, RecommendationAdapter.RecommendationViewHolder>(DIFF_CALLBACK) {

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
            binding.idRootRecom.setOnClickListener {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(recommendationItem.urlVideo));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                intent.setPackage("com.google.android.youtube")
                context.startActivity(intent)
            }
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
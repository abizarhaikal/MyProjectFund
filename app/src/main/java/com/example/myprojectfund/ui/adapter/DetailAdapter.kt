package com.example.myprojectfund.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myprojectfund.data.response.ItemsItem
import com.example.myprojectfund.databinding.ItemFollsBinding


class DetailAdapter : ListAdapter<ItemsItem ,DetailAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(val binding: ItemFollsBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(detail: ItemsItem) {
            Glide.with(binding.ivProfileDetail)
                .load(detail.avatarUrl)
                .into(binding.ivProfileDetail)
            binding.tvUserFragment.text = detail.login
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup ,viewType: Int): DetailAdapter.MyViewHolder {
        val binding = ItemFollsBinding.inflate(LayoutInflater.from(parent.context) ,parent ,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder ,position: Int) {
        val listFolls = getItem(position)
        holder.bind(listFolls)
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areContentsTheSame(oldItem: ItemsItem ,newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areItemsTheSame(oldItem: ItemsItem ,newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}
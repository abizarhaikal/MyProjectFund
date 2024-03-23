package com.example.myprojectfund.ui.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myprojectfund.data.response.ItemsItem
import com.example.myprojectfund.databinding.ItemListBinding

class GitHubAdapter : ListAdapter<ItemsItem ,GitHubAdapter.MyViewHolder>(DIFF_CALLBACK) {
    private lateinit var onItemClickCallback: OnItemClickCallback
    class MyViewHolder(val binding: ItemListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(listItem: ItemsItem) {
            Glide.with(binding.ivProfile)
                .load(listItem.avatarUrl)
                .into(binding.ivProfile)


            binding.tvUser.text = listItem.login

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup ,viewType: Int): GitHubAdapter.MyViewHolder {
        val binding = ItemListBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return MyViewHolder(binding)
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onBindViewHolder(holder: MyViewHolder ,position: Int) {
        val listItem = getItem(position)
        holder.bind(listItem)
        holder.itemView.setOnClickListener{onItemClickCallback.onItemClicked(listItem)}
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

    interface OnItemClickCallback {
        fun onItemClicked(listItem: ItemsItem)
    }

}



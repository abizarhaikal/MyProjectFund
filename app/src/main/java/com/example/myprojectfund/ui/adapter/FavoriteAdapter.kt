package com.example.myprojectfund.ui.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.myprojectfund.data.local.entity.GitEntity
import com.example.myprojectfund.databinding.ItemFollsBinding
import com.example.myprojectfund.ui.DetailActivity

class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {
    private val listGit = ArrayList<GitEntity>()
    fun setListGit(listGit: List<GitEntity>) {
        val diffCallback = GitDiffCallback(this.listGit, listGit)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listGit.clear()
        this.listGit.addAll(listGit)
        diffResult.dispatchUpdatesTo(this)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup ,viewType: Int): FavoriteViewHolder {
        val binding = ItemFollsBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return FavoriteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder ,position: Int) {
        holder.bind(listGit[position])
    }

    override fun getItemCount(): Int {
        return listGit.size
    }

    inner class FavoriteViewHolder(private val binding: ItemFollsBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(user : GitEntity) {
            with(binding) {
                tvUserFragment.text = user.username
                Glide.with(itemView.context)
                    .load(user.urlToImage)
                    .into(ivProfileDetail)
                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_USERNAME, user.username)
                    itemView.context.startActivity(intent)
                }
            }
        }

    }
}


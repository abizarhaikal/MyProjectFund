package com.example.myprojectfund.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import com.example.myprojectfund.data.local.entity.GitEntity

class GitDiffCallback(private val oldGitList: List<GitEntity>, private val newGitList: List<GitEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldGitList.size

    override fun getNewListSize(): Int = newGitList.size

    override fun areItemsTheSame(oldItemPosition: Int ,newItemPosition: Int): Boolean {
        return oldGitList[oldItemPosition].username == newGitList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int ,newItemPosition: Int): Boolean {
        val oldGit = oldGitList[oldItemPosition]
        val newGit = newGitList[newItemPosition]
        return oldGit.username == oldGit.username
    }
}
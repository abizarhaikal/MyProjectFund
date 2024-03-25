package com.example.myprojectfund.ui.viewmodel

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectfund.data.local.entity.GitEntity
import com.example.myprojectfund.repository.GitRepository

class FavoriteViewModel(application: Application): ViewModel() {
    private val mGitRepository: GitRepository = GitRepository(application)

    fun insert(user: GitEntity) {
        mGitRepository.insert(user)
    }

    fun delete(user: GitEntity) {
        mGitRepository.delete(user)
    }

    fun getAllUser(): LiveData<List<GitEntity>> = mGitRepository.getGitAll()

    fun getFavoriteUser(user: String): LiveData<GitEntity> = mGitRepository.getFavoriteUser(user)
}
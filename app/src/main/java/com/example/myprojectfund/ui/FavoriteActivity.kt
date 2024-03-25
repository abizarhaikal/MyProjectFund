package com.example.myprojectfund.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectfund.databinding.ActivityFavoriteBinding
import com.example.myprojectfund.ui.adapter.FavoriteAdapter
import com.example.myprojectfund.ui.viewmodel.FavoriteViewModel
import com.example.myprojectfund.ui.viewmodel.ViewModelFactory

class FavoriteActivity : AppCompatActivity() {

    private lateinit var activityFavoriteBinding: ActivityFavoriteBinding
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(application, pref = SettingPreferences.getInstance(application.dataStore))
    }
    private lateinit var adapterFavorite : FavoriteAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityFavoriteBinding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(activityFavoriteBinding.root)

        adapterFavorite = FavoriteAdapter()
//        val layoutManager = LinearLayoutManager(this)
        activityFavoriteBinding.rvFav.layoutManager = LinearLayoutManager(this)
        activityFavoriteBinding.rvFav.setHasFixedSize(true)
        favoriteViewModel.getAllUser().observe(this) {userList ->
            if (userList != null) {
                activityFavoriteBinding.rvFav.adapter = adapterFavorite
                adapterFavorite.setListGit(userList)
            }
        }
    }
}
package com.example.myprojectfund.ui

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myprojectfund.R
import com.example.myprojectfund.data.response.ItemsItem
import com.example.myprojectfund.databinding.ActivityMainBinding
import com.example.myprojectfund.ui.adapter.GitHubAdapter
import com.example.myprojectfund.ui.viewmodel.DetailViewModel
import com.example.myprojectfund.ui.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()
    private lateinit var adapter : GitHubAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        adapter = GitHubAdapter()
        adapter.setOnItemClickCallback(object : GitHubAdapter.OnItemClickCallback {
            override fun onItemClicked(listItem: ItemsItem) {
                Toast.makeText(applicationContext ,listItem.login ,Toast.LENGTH_SHORT).show()
                val userDetail = ViewModelProvider(this@MainActivity).get(DetailViewModel::class.java)

                val detailIntent = Intent(this@MainActivity, DetailActivity::class.java)
                detailIntent.putExtra(DetailActivity.EXTRA_USERNAME,listItem.login)
                startActivity(detailIntent)

            }

        })

        val settingPreferences = SettingPreferences.getInstance(application.dataStore)
//        val isDarkModeActive = settingPreferences.getThemeSetting()

        lifecycleScope.launch {
            settingPreferences.getThemeSetting().collect { isDarkModeActive ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            }
        }

        val layoutManager = LinearLayoutManager(this)
        activityMainBinding.rvMain.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this ,layoutManager.orientation)
        activityMainBinding.rvMain.addItemDecoration(itemDecoration)


        mainViewModel.listItem.observe(this) { item ->
            setListItem(item)
        }

        mainViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        mainViewModel.snackbarText.observe(this) {
            it.getContentIfNotHandled()?.let { snackbar ->
                Snackbar.make(
                    window.decorView.rootView ,
                    snackbar ,
                    Snackbar.LENGTH_SHORT
                ).show()

            }
        }


        with(activityMainBinding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { textView ,actionId ,event ->
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        val query = textView.text.toString()
                        val userView =
                            ViewModelProvider(this@MainActivity).get(MainViewModel::class.java)
                        userView.findOne(query)
                        searchView.hide()
                        true
                    } else {
                        false
                    }
                }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.item_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.favorite -> {
                val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                startActivity(intent)
            }
            R.id.setting -> {
                val intent = Intent(this@MainActivity, SettingActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }




    private fun setListItem(itemList: List<ItemsItem>?) {
        adapter.submitList(itemList)
        activityMainBinding.rvMain.adapter = adapter
    }

    private fun showLoading(isLoading: Boolean) {
        activityMainBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
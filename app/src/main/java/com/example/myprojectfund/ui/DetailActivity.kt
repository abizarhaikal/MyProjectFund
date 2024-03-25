package com.example.myprojectfund.ui

//import com.example.myprojectfund.ui.adapter.DetailAdapter
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myprojectfund.R
import com.example.myprojectfund.data.local.entity.GitEntity
import com.example.myprojectfund.data.response.ResponseDetail
import com.example.myprojectfund.databinding.ActivityDetailBinding
import com.example.myprojectfund.ui.adapter.SectionPagerAdapter
import com.example.myprojectfund.ui.viewmodel.DetailViewModel
import com.example.myprojectfund.ui.viewmodel.FavoriteViewModel
import com.example.myprojectfund.ui.viewmodel.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {
    private lateinit var activityDetailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()
    private val favoriteViewModel by viewModels<FavoriteViewModel>(){
        ViewModelFactory.getInstance(application, pref = SettingPreferences.getInstance(application.dataStore))
    }

    private var favUser : GitEntity? = null
    private var loveFab : Boolean? = null
    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text1,
            R.string.tab_text2
        )

        const val EXTRA_USERNAME = "extra_username"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityDetailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(activityDetailBinding.root)

        val sectionPagerAdapter = SectionPagerAdapter(this)
        val viewPager : ViewPager2 = activityDetailBinding.viewPager
        viewPager.adapter = sectionPagerAdapter
        val tabs : TabLayout = activityDetailBinding.tabs
        TabLayoutMediator(tabs,viewPager) {tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()


        supportActionBar?.elevation = 0f
        detailViewModel.name.observe(this) {user->
            favoriteViewModel.getFavoriteUser(user.login).observe(this) {favoriteUser ->
                if(favoriteUser != null) {
                    activityDetailBinding.fab.setImageDrawable(getDrawable(R.drawable.ic_fill_love))
                    loveFab = true
                } else {
                    activityDetailBinding.fab.setImageDrawable(getDrawable(R.drawable.ic_love))
                    loveFab = false
                }
            }
            activityDetailBinding.fab.setOnClickListener {
                val newLoveFabState = !loveFab!!
                if (user != null) {
                    favUser  = GitEntity(
                        username = user.login,
                        urlToImage = user.avatarUrl
                    )
                    if (newLoveFabState) {
                        favoriteViewModel.insert(favUser!!)
                        activityDetailBinding.fab.setImageDrawable(getDrawable(R.drawable.ic_fill_love))
                    } else {
                        favoriteViewModel.delete(favUser!!)
                        activityDetailBinding.fab.setImageDrawable(getDrawable(R.drawable.ic_love))
                        loveFab = true
                    }
                    loveFab = newLoveFabState
                }
            }
        }

        val nama = intent.getStringExtra(EXTRA_USERNAME).toString()

        supportActionBar?.title = nama
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        sectionPagerAdapter.username = nama

        detailViewModel.getUser(nama)

        detailViewModel.isLoading.observe(this) {
            showLoading(it)
        }

        detailViewModel.name.observe(this) {
            showDetail(it)
        }
    }

    private fun showDetail(user: ResponseDetail) {
        activityDetailBinding.tvUserDetail.text = user.name
        activityDetailBinding.tvUsername.text = "@" + user.login
        Glide.with(this)
            .load(user.avatarUrl)
            .into(activityDetailBinding.ivDetails)
        activityDetailBinding.edtFollowers.text = user.followers.toString()
        activityDetailBinding.edtFollowings.text = user.following.toString()
    }

    private fun showLoading(isLoading: Boolean) {
        activityDetailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
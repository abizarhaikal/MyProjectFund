package com.example.myprojectfund.ui

//import com.example.myprojectfund.ui.adapter.DetailAdapter
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.myprojectfund.R
import com.example.myprojectfund.data.response.ResponseDetail
import com.example.myprojectfund.databinding.ActivityDetailBinding
import com.example.myprojectfund.ui.adapter.SectionPagerAdapter
import com.example.myprojectfund.ui.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailActivity : AppCompatActivity() {

    private lateinit var activityDetailBinding: ActivityDetailBinding

    private val detailViewModel by viewModels<DetailViewModel>()

    companion object {
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text1,
            R.string.tab_text2
        )
    }

    //    private lateinit var adapter : DetailAdapter
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



        val nama = intent.getStringExtra("login").toString()

        sectionPagerAdapter.username = nama
//        val detailViewModel =
//            ViewModelProvider(this@DetailActivity).get(DetailViewModel::class.java)

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
//        Glide.with(this)
//            .load("https://i.pinimg.com/564x/ae/e5/2b/aee52bf04038ef221b51f708b0c6019a.jpg")
//            .into(activityDetailBinding.containerImage)

    }

    private fun showLoading(isLoading: Boolean) {
        activityDetailBinding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE

    }


}
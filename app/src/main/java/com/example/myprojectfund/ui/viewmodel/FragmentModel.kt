package com.example.myprojectfund.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectfund.data.response.ItemsItem
import com.example.myprojectfund.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FragmentModel : ViewModel() {
    private val _listItemDetail = MutableLiveData<List<ItemsItem>>()
    val listItemDetail: LiveData<List<ItemsItem>> = _listItemDetail

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    companion object {
        private const val TAG = "FragmentModel"
    }

    fun getFollowers(user: String) {
        val client = ApiConfig.getApiService().getFollowers(user)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>> ,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()
                    _listItemDetail.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG ,"OnFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>> ,t: Throwable) {
                _isLoading.value = false
                Log.e(TAG ,"onFailure: ${t.message}")
            }

        })
    }

    fun getFollowings(username: String) {
        val client = ApiConfig.getApiService().getFollowing(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>> ,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()
                    _listItemDetail.value = response.body()
                } else {
                    _isLoading.value = false
                    Log.e(TAG ,"onFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>> ,t: Throwable) {
                _isLoading.value = false
                Log.e(TAG ,"OnFailure : ${t.message}")
            }

        })
    }

}
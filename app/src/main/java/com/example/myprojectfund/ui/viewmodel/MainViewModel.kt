package com.example.myprojectfund.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectfund.data.response.ItemsItem
import com.example.myprojectfund.data.response.ResponseGitHub
import com.example.myprojectfund.data.retrofit.ApiConfig
import com.example.myprojectfund.util.Event
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _listItem = MutableLiveData<List<ItemsItem>>()
    val listItem: LiveData<List<ItemsItem>> = _listItem

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val _snackbarText = MutableLiveData<Event<String>>()
    val snackbarText: LiveData<Event<String>> = _snackbarText



    companion object {
        private const val TAG = "MainActivity"
        private const val QUERY = "q"

    }

    init {
        findUser()

    }

    fun findOne(query: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getUsers(query)
        client.enqueue(object : Callback<ResponseGitHub> {
            override fun onResponse(
                call: Call<ResponseGitHub> ,
                response: Response<ResponseGitHub>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val items = response.body()?.items
                    _listItem.value = response.body()?.items

                    if(items.isNullOrEmpty()) {
                        _snackbarText.value = Event(response.body()?.incompleteResults.toString())
                    }

                } else {
                    Log.e(TAG ,"OnFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGitHub> ,t: Throwable) {
                Log.e(TAG ,"OnFailure : ${t.message}")
            }
        })

    }


    private fun findUser() {
        val client = ApiConfig.getApiService().getUsers(QUERY)
        client.enqueue(object : Callback<ResponseGitHub> {
            override fun onResponse(
                call: Call<ResponseGitHub> ,
                response: Response<ResponseGitHub>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    _listItem.value = response.body()?.items
                } else {
                    Log.e(TAG ,"onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseGitHub> ,t: Throwable) {
                _isLoading.value = false
                Log.e(TAG ,"onFailure: ${t.message}")
            }
        })
    }

}
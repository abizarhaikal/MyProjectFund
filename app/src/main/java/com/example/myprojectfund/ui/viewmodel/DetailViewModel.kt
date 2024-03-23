package com.example.myprojectfund.ui.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myprojectfund.data.response.ResponseDetail
import com.example.myprojectfund.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    companion object {
        const val TAG = "DetailActivity"
    }
    private val _name = MutableLiveData<ResponseDetail>()
    val name : LiveData<ResponseDetail> = _name

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> = _isLoading


    fun getUser(user : String) {
        val client = ApiConfig.getApiService().getDetails(user)
        client.enqueue(object : Callback<ResponseDetail> {
            override fun onResponse(
                call: Call<ResponseDetail> ,
                response: Response<ResponseDetail>
            ) {
                _isLoading.value = false
                if (response.isSuccessful){
                    _name.value = response.body()
                } else {
                    Log.e(TAG, "OnFailure : ${response.message()}")
                }
            }

            override fun onFailure(call: Call<ResponseDetail> ,t: Throwable) {
                Log.e(TAG, "OnFailure : ${t.message}")
            }

        })

    }

    private fun getDetail() {
        val client = ApiConfig.getApiService()
    }
}
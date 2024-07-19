package com.example.openinappproject.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.openinappproject.model.OpenInApp
import com.example.openinappproject.network.RetrofitInstance
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AppViewModel : ViewModel() {
    private val _openInAppResponse = MutableSharedFlow<OpenInApp>()
    val openInAppResponse : SharedFlow<OpenInApp> = _openInAppResponse
    private val api = RetrofitInstance.getInstance()
    fun fetchResponse() {
        api.getDashboardData().enqueue(object : Callback<OpenInApp> {
            override fun onResponse(call: Call<OpenInApp>, response: Response<OpenInApp>) {
                if(response.isSuccessful && response.body() != null) {
                    Log.d("AppViewModel", "onResponse: ${response.body()}")
                    CoroutineScope(Dispatchers.Main).launch {
                        _openInAppResponse.emit(response.body()!!)
                    }
                }
            }

            override fun onFailure(call: Call<OpenInApp>, t: Throwable) {

            }

        })
    }
}

interface ApiResponse {
    fun onResponse(openInApp: OpenInApp)
}
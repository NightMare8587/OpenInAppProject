package com.example.openinappproject.network

import com.example.openinappproject.model.OpenInApp
import retrofit2.Call
import retrofit2.http.GET

interface Api {
    @GET("dashboardNew")
    fun getDashboardData() : Call<OpenInApp>
}
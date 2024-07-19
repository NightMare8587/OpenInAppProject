package com.example.openinappproject.network

import com.example.openinappproject.constants.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private var instance : Api? = null
    val authInterceptor = AuthInterceptor("eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MjU5MjcsImlhdCI6MTY3NDU1MDQ1MH0.dCkW0ox8tbjJA2GgUx2UEwNlbTZ7Rr38PVFJevYcXFI")
    val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(authInterceptor)
        .build()
    fun getInstance() : Api {
        if(instance != null)
            return instance!!
        else {
            val retrofit = Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build().create(Api::class.java)
            instance = retrofit
            return retrofit
        }
    }
}
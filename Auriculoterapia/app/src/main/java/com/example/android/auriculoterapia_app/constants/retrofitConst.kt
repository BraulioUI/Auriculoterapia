package com.example.android.auriculoterapia_app.constants

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class ApiClient{
    companion object{
        fun retrofit(): Retrofit{
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        }
    }
}

package com.example.android.auriculoterapia_app.services


import com.example.android.auriculoterapia_app.models.TipoAtencion
import retrofit2.Call
import retrofit2.http.GET

interface AttentionService {
    @GET("api/tipoatencion/")
    fun listAttentionTypes(): Call<List<TipoAtencion>>
}
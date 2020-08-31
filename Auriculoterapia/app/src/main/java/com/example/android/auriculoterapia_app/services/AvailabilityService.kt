package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Disponibilidad
import com.example.android.auriculoterapia_app.models.helpers.FormularioDisponibilidad
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AvailabilityService {

    @POST("api/disponibilidad")
    fun saveAvailability(@Body form: FormularioDisponibilidad, @Query("especialistaId") especialistaId: Int)
                        : Call<FormularioDisponibilidad>

    @GET("api/disponibilidad")
    fun getAvailabilityByDate(@Query("fecha") fecha: String): Call<Disponibilidad>
}
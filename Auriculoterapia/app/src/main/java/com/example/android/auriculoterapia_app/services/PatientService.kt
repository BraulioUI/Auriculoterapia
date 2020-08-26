package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Paciente
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface PatientService {

    @GET("api/paciente/")
    fun listPatients(): Call<List<Paciente>>

    @POST("api/paciente")
    fun registerPatient(@Body paciente: Paciente):Call<Paciente>
}
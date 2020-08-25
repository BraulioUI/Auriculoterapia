package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Paciente
import retrofit2.Call
import retrofit2.http.GET

interface PatientService {

    @GET("api/paciente/")
    fun listPatients(): Call<List<Paciente>>
}
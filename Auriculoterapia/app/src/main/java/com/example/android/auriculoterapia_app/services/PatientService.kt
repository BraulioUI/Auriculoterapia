package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.helpers.CantidadPacientesPorSexo
import retrofit2.Call
import retrofit2.http.*

interface PatientService {

    @GET("api/paciente")
    fun listPatientsByWord(@Header("Authorization") token: String, @Query("word") key: String): Call<List<Paciente>>

    @GET("api/paciente/lista")
    fun listPatients(@Header("Authorization") token: String): Call<List<Paciente>>

    @POST("api/paciente")
    fun registerPatient(@Body paciente: Paciente):Call<Paciente>

    @GET("api/paciente/pacientesPorSexo")
    fun obtenerCantidadPacientesPorSexo(@Query("tratamiento") tratamiento: String): Call<CantidadPacientesPorSexo>
}
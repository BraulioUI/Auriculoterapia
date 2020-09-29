package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.helpers.*
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
    fun obtenerCantidadPacientesPorSexo(): Call<CantidadPacientesPorSexo>

    @GET("api/paciente/pacientesPorEdad")
    fun obtenerCantidadPacientesPorEdad(): Call<CantidadPacientesPorEdad>

    @GET("api/paciente/pacientesPorNivelDeMejora")
    fun obtenerCantidadPacientesPorNivelDeMejora(): Call<CantidadPacientesPorNivelMejora>

    @GET("api/paciente/pacientesObesidad")
    fun retornarResultadosPacientesObesidad(@Query("sexo") sexo: String): Call<List<ResponsePacientesObesidad>>

    @GET("api/paciente/{id}")
    fun resultParametersByPacienteId(@Path("id") id: Int): Call<PacienteResultsParameters>


}
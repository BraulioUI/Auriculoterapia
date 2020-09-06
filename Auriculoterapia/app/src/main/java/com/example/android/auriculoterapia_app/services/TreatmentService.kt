package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Tratamiento
import com.example.android.auriculoterapia_app.models.helpers.FormularioTratamiento
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface TreatmentService {

    @POST("api/tratamiento")
    fun registerTreatment(@Body body: FormularioTratamiento): Call<Boolean>

    @GET("api/tratamiento/historial")
    fun getByPatientId(@Query("pacienteId") pacienteId: Int): Call<List<Tratamiento>>
}
package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.models.FormularioCita
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface AppointmentService {

    @POST("api/cita/")
    fun registerAppointment(@Body newAppointment: FormularioCita,
                            @Query("PacienteId") patientId: Int): Call<FormularioCita>
    @GET("api/cita/")
    fun listAppointment(): Call<List<Cita>>
}
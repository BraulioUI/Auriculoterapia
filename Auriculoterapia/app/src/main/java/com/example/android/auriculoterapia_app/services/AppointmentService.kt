package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Cita
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface AppointmentService {

    @POST("api/cita/")
    fun registerAppointment(@Body newAppointment: Cita,
                            @Query("PacienteId") patientId: Int): Call<Cita>

}
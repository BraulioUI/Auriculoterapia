package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Appointment
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface AppointmentService {

    @POST("cita")
    fun registerAppointment(@Body newAppointment: Appointment,
                            @Query("PacienteId") patientId: Int): Call<List<Appointment>>

}
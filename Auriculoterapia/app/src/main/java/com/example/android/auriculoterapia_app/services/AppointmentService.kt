package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Cita
import com.example.android.auriculoterapia_app.models.helpers.FormularioCita
import com.example.android.auriculoterapia_app.models.helpers.FormularioCitaPaciente
import retrofit2.Call
import retrofit2.http.*

interface AppointmentService {

    @POST("api/cita/especialista")
    fun registerAppointment(@Body newAppointment: FormularioCita,
                            @Query("PacienteId") patientId: Int): Call<FormularioCita>

    @POST("api/cita/paciente")
    fun registerAppointmentPatient(@Body newAppointment: FormularioCitaPaciente,
                                   @Query("PacienteId") patientId: Int): Call<FormularioCitaPaciente>
    @GET("api/cita/")
    fun listAppointment(): Call<List<Cita>>

    @GET("api/cita/paciente")
    fun listPatientAppointment(@Header("Authorization") token: String, @Query("usuarioId") usuarioId: Int): Call<List<Cita>>

    @PUT("api/cita/estado")
    fun updateStateOfAppointment(@Query("citaId") citaId: Int, @Query("estado") estado: String): Call<Boolean>

    @GET("api/cita/{id}")
    fun findAppointmentById(@Header("Authorization") token: String, @Path("id") citaId: Int): Call<Cita>

    @PUT("api/cita/{id}")
    fun updateAppointmentForPatient(@Header("Authorization") token: String,
                                    @Path("id") citaId: Int,
                                    @Body form: FormularioCitaPaciente): Call<Boolean>
}
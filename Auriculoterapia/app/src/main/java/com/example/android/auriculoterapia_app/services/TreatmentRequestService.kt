package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import retrofit2.Call
import retrofit2.http.*

interface TreatmentRequestService{

    @GET("api/solicitudtratamiento")
    fun findByPacienteId(@Query("pacienteId") pacienteId: Int): Call<SolicitudTratamiento>

    @POST("api/solicitudTratamiento/{userId}")
    fun registerTreatment(@Header("Authorization") token:String,
                          @Path("userId") userId:Int,
                          @Body treatmentRequest: SolicitudTratamiento): Call<SolicitudTratamiento>

}
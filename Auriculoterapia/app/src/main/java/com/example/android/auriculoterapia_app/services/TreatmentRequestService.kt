package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import retrofit2.Call
import retrofit2.http.*

interface TreatmentRequestService{

    @GET("api/solicitudtratamiento")
    fun findByPacienteId(@Query("pacienteId") pacienteId: Int, @Header("Authorization") token:String): Call<SolicitudTratamiento>

    @POST("api/solicitudTratamiento/{userId}")
    fun registerTreatment(@Header("Authorization") token:String,
                          @Path("userId") userId:Int,
                          @Body treatmentRequest: SolicitudTratamiento): Call<SolicitudTratamiento>

    @GET("api/solicitudtratamiento/imagen")
    fun findImageByRequest(@Header("Authorization") token: String,
                            @Query("solicitudId") solicitudId: Int): Call<String>
}
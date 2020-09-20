package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Tratamiento
import com.example.android.auriculoterapia_app.models.helpers.FormularioTratamiento
import retrofit2.Call
import retrofit2.http.*

interface TreatmentService {

    @POST("api/tratamiento")
    fun registerTreatment(@Body body: FormularioTratamiento): Call<Boolean>

    @GET("api/tratamiento/historial")
    fun getByPatientId(@Query("pacienteId") pacienteId: Int): Call<List<Tratamiento>>

    @GET("api/tratamiento/{id}")
    fun getById(@Path("id")id:Int):Call<Tratamiento>

    @POST("api/evolucion/{IdPaciente}")
    fun registerFormTreatmentEvolucion(@Body body:FormularioEvolucion,
                                       @Path("IdPaciente")IdPaciente:Int):Call<FormularioEvolucion>

    @GET("api/evolucion/{IdPaciente}/{TipoTratamiento}")
    fun getByIdPacienteTipoTratamiento(@Path("TipoTratamiento")TipoTratamiento:String,
                                       @Path("IdPaciente")IdPaciente: Int):Call<List<FormularioEvolucion>>

    @GET("api/evolucion/{IdPaciente}/{TipoTratamiento}")
    fun getByIdPacienteTipoTratamientoResults(@Path("TipoTratamiento")TipoTratamiento:String,
                                       @Path("IdPaciente")IdPaciente: Int):Call<List<ResultsByPatient>>
}

data class FormularioEvolucion(var evolucionNumero:Int,var peso:Double,
                          var otros:String,var tipoTratamiento:String,var tratamientoId:Int,var sesion:Int?)

data class ResultsByPatient(var evolucionNumero:Int,var peso:Double,var tipoTratamiento:String,var tratamientoId:Int,var sesion:Int?,var imc:Double,var grasaCorporal:Double)
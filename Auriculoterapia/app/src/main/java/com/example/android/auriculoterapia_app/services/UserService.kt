package com.example.android.auriculoterapia_app.services

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface UserService {
    @POST("api/usuario/actualizarContrasena")
    fun forgotPassword(@Body forgotPasswordRequest:ForgotPasswordRequest):Call<ForgotPasswordRequest>

    @GET("api/usuario/{id}")
    fun getUserById(@Path("id") id:Int): Call<ResponseUserById>

    @POST("api/usuario/actualizarpalabraclave")
    fun updateKeyWord(@Body responseKeyWord: ResponseKeyWord):Call<ResponseKeyWord>
}

class ForgotPasswordRequest(var nombreUsuario:String,var palabraClave:String ,var contrasena: String)

class ResponseUserById(var id:Int,var nombre:String,var apellido:String,var email:String,var contrasena:String,
    var nombreUsuario:String,var sexo:String,var palabraClave:String,var fechaNacimiento:String?,var edad:Int)

class ResponseKeyWord(var id:Int, var palabraClave:String, var nuevaPalabraClave: String)
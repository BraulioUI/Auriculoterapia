package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.RespuestaLogin
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/usuario/autenticar")
    fun authenticate(@Body authRequest:AuthRequest): Call<RespuestaLogin>
}
class AuthRequest(var nombreUsuario:String, var contrasena: String)

class AuthResponse(var id: Int,var usuario:String)

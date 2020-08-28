package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/usuario/autenticar")
    fun authenticate(@Body authRequest:AuthRequest): Call<AuthResponse>
}
class AuthRequest(var nombreUsuario:String, var contrasena: String)

data class AuthResponse(var id: Int,var nombreUsuario:String,var token:String,var rol:String)

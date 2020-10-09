package com.example.android.auriculoterapia_app.services

import com.example.android.auriculoterapia_app.models.Notificacion
import retrofit2.Call
import retrofit2.http.*

interface NotificationService {

    @POST("api/notificacion")
    fun postNotification(@Body notificacion: Notificacion): Call<Notificacion>

    @GET("api/notificacion/{id}")
    fun getAllNotificationsByReceptorId(@Path("id") receptorId: Int): Call<List<Notificacion>>

    @PUT("api/notificacion/deshabilitar/{id}")
    fun disableNotification(@Path("id") notificationId: Int): Call<Boolean>

    @GET("api/notificacion/{receptorId}/cantidad")
    fun numeroNotificacionesNoLeidas(@Path("receptorId") receptorId: Int): Call<Int>

    @PUT("api/notificacion/leer")
    fun leerNotificaciones(@Query("receptorId") receptorId: Int): Call<Boolean>
}
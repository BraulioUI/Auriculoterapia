package com.example.android.auriculoterapia_app.activities

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.NotificationsAdapter
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.Notificacion
import com.example.android.auriculoterapia_app.services.NotificationService
import kotlinx.android.synthetic.main.activity_notifications.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationsActivity : AppCompatActivity() {

    lateinit private var notifcationRecycler: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notifications)

        val actionBar = supportActionBar
        actionBar!!.title = "Notificaciones"



        var userId = 0
        intent.extras?.let{
            val bundle = it
            userId = it.getInt("id")
        }

        val notificacionService = ApiClient.retrofit().create(NotificationService::class.java)


        notifcationRecycler = findViewById(R.id.notificationRecycler)
        notificationRecycler.layoutManager = LinearLayoutManager(this)
        val notificationsAdapter = NotificationsAdapter()
        notificationRecycler.adapter = notificationsAdapter

        val textSinNotificaciones = findViewById<TextView>(R.id.sin_notificaciones_text)

        notificacionService.getAllNotificationsByReceptorId(userId).enqueue(object:
            Callback<List<Notificacion>> {
            override fun onFailure(call: Call<List<Notificacion>>, t: Throwable) {
                Log.i("F", "Fcito")
            }

            override fun onResponse(
                call: Call<List<Notificacion>>,
                response: Response<List<Notificacion>>
            ) {
                if(response.body() == null){
                    textSinNotificaciones.visibility = View.VISIBLE
                } else {
                    if(response.isSuccessful){
                        Log.i("Response", response.body().toString())

                        if(response.body()!!.size == 0){

                            textSinNotificaciones.visibility = View.VISIBLE
                        } else{
                            val notificaciones = response.body() as ArrayList

                            textSinNotificaciones.visibility = View.GONE

                            notificationsAdapter.submitList(notificaciones)
                            val itemTouchHelper = ItemTouchHelper(
                                object: ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT){

                                    override fun onMove(
                                        recyclerView: RecyclerView,
                                        viewHolder: ViewHolder,
                                        target: ViewHolder
                                    ): Boolean {
                                        TODO("Not yet implemented")
                                    }

                                    override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
                                        val position = viewHolder.adapterPosition

                                        when(direction){
                                            ItemTouchHelper.RIGHT -> {
                                                val notif = notificationsAdapter.getNotificationByPosition(position)
                                                deshabilitarNotificacion(notif.id, notificacionService)
                                                notificationsAdapter.removeElement(position)

                                                Log.i("Size", "${notificationsAdapter.getItemCount()}")

                                            }
                                        }
                                    }
                                }
                            )
                            itemTouchHelper.attachToRecyclerView(notifcationRecycler)

                        }


                    }
                }


            }
        })

        leerNotificaciones(userId, notificacionService)




    }

    fun deshabilitarNotificacion(notificationId: Int, notificacionService: NotificationService){
        notificacionService.disableNotification(notificationId).enqueue(object: Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.i("Fallo", "No se pudo deshabilitar la notificacion")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body()!!){
                        Log.i("Exito", "Notificacion ${notificationId} deshabilitada")
                    }
                }
            }
        })
    }

    fun leerNotificaciones(id:Int, notificacionService: NotificationService){
        notificacionService.leerNotificaciones(id).enqueue(object : Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Log.i("Fallo", "No se pudieron leer las notificaciones")
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
                if(response.isSuccessful){
                    if(response.body()!!){
                        Log.i("Exito", "Notificaciones leídas")
                    }
                }
            }
        })
    }
}
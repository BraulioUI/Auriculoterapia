package com.example.android.auriculoterapia_app.activities

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.adapters.NotificationsAdapter
import com.example.android.auriculoterapia_app.models.Notificacion
import kotlinx.android.synthetic.main.activity_notifications.*


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

        notifcationRecycler = findViewById(R.id.notificationRecycler)
        notificationRecycler.layoutManager = LinearLayoutManager(this)
        val notificationsAdapter = NotificationsAdapter()
        notificationRecycler.adapter = notificationsAdapter

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
                            notificationsAdapter.removeElement(position)
                            Log.i("Size", "${notificationsAdapter.getItemCount()}")
                        }
                    }
                }
            }
        )
        itemTouchHelper.attachToRecyclerView(notifcationRecycler)



        val notificaciones = arrayListOf(
            Notificacion(2,1,"Nueva cita", false, "2020-10-07",
            "19:33", "Marcos Rivas", "Acaba de registrar una nueva cita"),
            Notificacion(2,1,"Nueva cita", false, "2020-10-07",
                "19:34", "Angel Huerta", "Acaba de registrar una nueva cita"),
            Notificacion(2,1,"Nueva cita", false, "2020-10-07",
                "19:37", "Angel Huerta", "Acaba de modificar una cita")

        )

        notificationsAdapter.submitList(notificaciones)


    }
}
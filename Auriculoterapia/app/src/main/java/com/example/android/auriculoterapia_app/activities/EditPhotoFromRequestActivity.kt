package com.example.android.auriculoterapia_app.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditPhotoFromRequestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo_from_request)

        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token", "")
        val imagenEditable = findViewById<ImageView>(R.id.imagenFotoAEditar)

        var solicitudTratamientoId = 0
        intent.extras?.let{
            val bundle: Bundle = it
            solicitudTratamientoId = bundle.getInt("solicitudTratamientoId")
        }

        val solicitudService = ApiClient.retrofit().create(TreatmentRequestService::class.java)

        solicitudService.findImageByRequest(token!!, solicitudTratamientoId).enqueue(object: Callback<String>{
            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.i("Error", "Fallo al recuperar la imagen")
            }

            override fun onResponse(call: Call<String>, response: Response<String>) {
                if(response.isSuccessful){
                    val imagen = response.body()!!
                    Log.i("URL", imagen)
                   /* Glide.with(imagenEditable.context)
                        .load(imagen)
                        .into(imagenEditable)*/


                }
            }
        })

    }


}
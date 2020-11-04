package com.example.android.auriculoterapia_app.util

import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.services.ResponseValidationEmail
import com.example.android.auriculoterapia_app.services.UserService
import com.google.gson.JsonParser
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ValidateEmail: AppCompatDialogFragment() {

    val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)
    var userId = -10
    lateinit var codigoValidar :EditText

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        val inflaterValidateEmail = requireActivity().layoutInflater

        val viewValidateEmail = inflaterValidateEmail.inflate(R.layout.layout_email_validate,null)


        val validarButton = viewValidateEmail.findViewById<Button>(R.id.btn_validarCorreo2)

        codigoValidar = viewValidateEmail.findViewById(R.id.et_codigovalidacion)


        arguments?.let {
            val bundle: Bundle = it
            userId = bundle.getInt("IDUSER",-10)
        }


        validarButton.setOnClickListener {
            validar()
        }

        builder.setView(viewValidateEmail).setTitle("Validar Correo")
            .setNegativeButton("Cerrar",DialogInterface.OnClickListener{
                dialog,id ->
                dialog.dismiss()
            })

        val dialog = builder.create()
        return dialog

    }

    private fun validar(){

        val validationEmail = ResponseValidationEmail(userId,codigoValidar.text.toString(),false)


        userService.validateEmail(validationEmail).enqueue(object : Callback<ResponseValidationEmail>{
            override fun onFailure(call: Call<ResponseValidationEmail>, t: Throwable) {
                Log.i("VALIDAR CORREO: ", "NO ENTRO")
            }

            override fun onResponse(
                call: Call<ResponseValidationEmail>,
                response: Response<ResponseValidationEmail>
            ) {
                if (response.isSuccessful){
                    Toast.makeText(requireContext(),"Correo validado con exito", Toast.LENGTH_SHORT).show()
                }else{
                    val res = response.errorBody()?.string()
                    val message = JsonParser().parse(res).asJsonObject["message"].asString

                    Toast.makeText(requireContext(),message, Toast.LENGTH_SHORT).show()
                    Log.i("VALIDAR CORREO: ", "WHAT FUEEE")
                }
            }

        })
    }


}
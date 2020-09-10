package com.example.android.auriculoterapia_app.activities

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import com.example.android.auriculoterapia_app.MainActivity
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.Paciente
import com.example.android.auriculoterapia_app.models.Usuario
import com.example.android.auriculoterapia_app.services.AuthService
import com.example.android.auriculoterapia_app.services.PatientService
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_register.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    lateinit var optionGenero: Spinner
    lateinit var resultGenero: TextView
    var completeAll: Boolean = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        optionGenero = findViewById(R.id.spinner3)
        resultGenero = findViewById(R.id.tv_resultadoGenero)
        resultGenero.visibility = View.GONE

        val options = arrayOf("masculino","femenino")

        optionGenero.adapter = ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,options)

        optionGenero.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                resultGenero.text = "Seleccionar:"
            }

            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                resultGenero.text = options.get(position)
            }

        }


        //DatePickerDialog actions
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)


        val dateEditText = findViewById<TextView>(R.id.et_Fecha)
        //val dateButton = findViewById<TextView>(R.id.et_Fecha)
        val date = Calendar.getInstance().time

        //val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        //val dateInString = formatter.format(date)
        //dateEditText.text = dateInString

        dateEditText.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    view, mYear, mMonth, mDay -> dateEditText.text = "$mYear-${mMonth + 1}-$mDay"
            }, year, month, day
            )
            c.add(Calendar.YEAR,-61)
            c.add(Calendar.DATE,1)
            dpd.datePicker.minDate = c.timeInMillis
            c.add(Calendar.YEAR, 61 -14)
            c.add(Calendar.DATE,-1)
            dpd.datePicker.maxDate = c.timeInMillis
            dpd.show()
        }



        //val intent = Intent(this, MainActivity::class.java)
        buttonRegister.setOnClickListener{
            completeAll = true
            registrarPaciente()
        }

    }

    private fun registrarPaciente(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()



        val pacienteService = retrofit.create<PatientService>(PatientService::class.java)
        val nombre = findViewById<EditText>(R.id.et_nombreRegistro)
        val apellido = findViewById<EditText>(R.id.et_apellido)
        val fechaNacimiento = findViewById<EditText>(R.id.et_Fecha)
        val sexo = findViewById<TextView>(R.id.tv_resultadoGenero)
        val nombreUsuario = findViewById<EditText>(R.id.et_usuario)
        val contrasena = findViewById<EditText>(R.id.et_password)
        val palabraClave = findViewById<EditText>(R.id.et_keyword)
        val email = findViewById<EditText>(R.id.et_email)

        val intentLogIn = Intent(this, LogInActivity::class.java)

        if(nombre.text.isEmpty()|| apellido.text.isEmpty() || fechaNacimiento.text.isEmpty()||
            sexo.text.isEmpty()|| nombreUsuario.text.isEmpty() ||
            contrasena.text.isEmpty()||palabraClave.text.isEmpty()||
            email.text.isEmpty()){
            Toast.makeText(applicationContext,
                "Por favor complete todos los campos",Toast.LENGTH_SHORT).show()
            completeAll = false

        }

        if (nombreUsuario.text.toString().length <2 || nombreUsuario.text.toString().length >15){
            nombreUsuario.setError("Debe contener entre 2 y 15 caracteres")
            nombreUsuario.setText("")
            nombreUsuario.requestFocus()
            completeAll = false
        }
        if (nombre.text.toString().length < 2 || nombre.text.toString().length > 15){
            nombre.setError("Debe contener entre 2 y 15 caracteres")
            nombre.setText("")
            nombre.requestFocus()
            completeAll = false
        }

        if(apellido.text.toString().length < 2 || apellido.text.toString().length >15){
            apellido.setError("Debe contener entre 2 y 15 caracteres")
            apellido.setText("")
            apellido.requestFocus()
            completeAll = false
        }

        if (contrasena.text.toString().length < 6 || contrasena.text.toString().length > 15){
            contrasena.setError("La contraseña de tener al menos 6 caracteres")
            contrasena.setText("")
            contrasena.requestFocus()
            completeAll = false
        }
        if (palabraClave.text.toString().length < 4 || palabraClave.text.toString().length > 15){
            palabraClave.setError("La palabra clave de tener al menos 4 caracteres")
            palabraClave.setText("")
            palabraClave.requestFocus()
            completeAll = false
        }
        if(fechaNacimiento.text.isEmpty()){
            fechaNacimiento.setError("Selecciona su fecha de nacimiento")
            fechaNacimiento.requestFocus()
            completeAll = false
        }
        if(email.text.isEmpty()){
            email.setError("Ingrese su correo")
            email.requestFocus()
            completeAll = false
        }

        if(completeAll){
            val usuario = Usuario(null,nombre.text.toString(),apellido.text.toString(),
                email.text.toString(),
                contrasena.text.toString(),nombreUsuario.text.toString(),sexo.text.toString(),
                palabraClave.text.toString(),
                null)

            val paciente = Paciente(null,fechaNacimiento.text.toString(),"",usuario)

            pacienteService.registerPatient(paciente).enqueue(object : Callback<Paciente>{
                override fun onFailure(call: Call<Paciente>, t: Throwable) {
                    Log.i("REGISTRAR PACIENTE","NO ENTRO")
                }

                override fun onResponse(call: Call<Paciente>, response: Response<Paciente>) {
                    if(response.isSuccessful){
                        Log.i("REGISTRAR PACIENTE: ", response.body().toString())
                        Toast.makeText(applicationContext,
                            "Se registró el usuario correctamente, por favor inicie sesión",Toast.LENGTH_SHORT).show()
                        startActivity(intentLogIn)
                    }
                    else{
                        when(response.code()){
                            400 ->{
                                val res = response.errorBody()?.string()
                                val message = JsonParser().parse(res).asJsonObject["message"].asString

                                Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }

            })
        }


    }
}
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
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateInString = formatter.format(date)
        dateEditText.text = dateInString

        dateEditText.setOnClickListener{
            val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener{
                    view, mYear, mMonth, mDay -> dateEditText.text = "$mYear-${mMonth + 1}-$mDay"
            }, year, month, day
            )

            dpd.show()
        }



        //val intent = Intent(this, MainActivity::class.java)
        buttonRegister.setOnClickListener{
            registrarPaciente()
        }

    }

    private fun registrarPaciente(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val pacienteService = retrofit.create<PatientService>(PatientService::class.java)
        val nombre = findViewById<EditText>(R.id.et_nombreRegistro).text
        val apellido = findViewById<EditText>(R.id.et_apellido).text
        val fechaNacimiento = findViewById<EditText>(R.id.et_Fecha).text
        val sexo = findViewById<TextView>(R.id.tv_resultadoGenero).text
        val nombreUsuario = findViewById<EditText>(R.id.et_usuario).text
        val contrasena = findViewById<EditText>(R.id.et_password).text
        val palabraClave = findViewById<EditText>(R.id.et_keyword).text
        val email = findViewById<EditText>(R.id.et_email).text

        val intentLogIn = Intent(this, LogInActivity::class.java)

        if(nombre.isEmpty()|| apellido.isEmpty() || fechaNacimiento.isEmpty()||sexo.isEmpty()|| nombreUsuario.isEmpty() || contrasena.isEmpty()||palabraClave.isEmpty()||email.isEmpty()){
            Toast.makeText(applicationContext,"Por favor complete todos los campos",Toast.LENGTH_SHORT).show()
        }else{
            val usuario = Usuario(null,nombre.toString(),apellido.toString(),email.toString(),
                contrasena.toString(),nombreUsuario.toString(),sexo.toString(),palabraClave.toString(),
                null)

            val paciente = Paciente(null,fechaNacimiento.toString(),"",usuario)

            pacienteService.registerPatient(paciente).enqueue(object : Callback<Paciente>{
                override fun onFailure(call: Call<Paciente>, t: Throwable) {
                    Log.i("REGISTRAR PACIENTE","NO ENTRO")
                }

                override fun onResponse(call: Call<Paciente>, response: Response<Paciente>) {
                    if(response.isSuccessful){
                        Log.i("REGISTRAR PACIENTE: ", response.body().toString())
                        Toast.makeText(applicationContext,"Se registró el usuario correctamente, por favor inicie sesión",Toast.LENGTH_SHORT).show()
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
package com.example.android.auriculoterapia_app.activities

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.example.android.auriculoterapia_app.MainActivity
import com.example.android.auriculoterapia_app.MainActivityPatient
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.BASE_URL
import com.example.android.auriculoterapia_app.models.RespuestaLogin
import com.example.android.auriculoterapia_app.models.Usuario
import com.example.android.auriculoterapia_app.services.*
import com.example.android.auriculoterapia_app.util.ValidateEmail
import com.google.gson.Gson
import com.google.gson.JsonParser
import kotlinx.android.synthetic.main.activity_log_in.*
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class LogInActivity : AppCompatActivity() {

    var completeAll: Boolean = true
    val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)
    lateinit var bundleToDialog: Bundle
    var isCreate: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        
        val sharedPreferences = getSharedPreferences("db_auriculoterapia",0)

        val intentMainPatient = Intent(this, MainActivityPatient::class.java)
        val intentMain = Intent(this, MainActivity::class.java)

        val iduser =sharedPreferences.getInt("id",-10)
        val existEmailSharedPreferences = sharedPreferences.getBoolean("ExistEmail",false)
        AuxUser = iduser

        super.onCreate(savedInstanceState)

        Log.i("ISCREATE: ", isCreate.toString())

        validarCorreoUsuario(AuxUser,"ninguno")

        Log.i("EXISTEMAILCREATE: ", existEmailSharedPreferences.toString())


        Log.i("IDDDDDDDDDD: ", AuxUser.toString())

        if(sharedPreferences.contains("id") && existEmailSharedPreferences){
            Log.i("IDDDDDDDDDD: ", AuxUser.toString())

            userService.getUserById(iduser).enqueue(object:Callback<ResponseUserById>{
                override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                    Log.i("LOGIN: ","NO HAY CONEXION")
                    setContentView(R.layout.activity_log_in)
                }

                override fun onResponse(
                    call: Call<ResponseUserById>,
                    response: Response<ResponseUserById>
                ) {
                    if(response.isSuccessful){
                        if(sharedPreferences.getString("rol",null)=="PACIENTE"){
                            startActivity(intentMainPatient)
                        }else{
                            startActivity(intentMain)
                        }
                    }else{
                        Log.i("LOGIN: ","TODAVIA NO INICA SESION")
                        //setContentView(R.layout.activity_log_in)

                    }
                }
            })


        }else{
            setContentView(R.layout.activity_log_in)


            val intentRegister = Intent(this, RegisterActivity::class.java)
            val intentForget = Intent(this, ForgotPasswordActivity::class.java)

            val registerButton = findViewById<TextView>(R.id.tv_optionregister)
            val forgetButton = findViewById<TextView>(R.id.tv_optionForgetPassword)
            val loginButton = findViewById<Button>(R.id.bt_login)
            val validarCorreoButton = findViewById<Button>(R.id.btn_validarCorreo)
            val nombreUsuario = findViewById<EditText>(R.id.et_nombreUsuario)


            val validarDialog = ValidateEmail()

            validarCorreoButton.visibility = View.GONE


            loginButton.setOnClickListener{
                if(ExistEmail) {
                    completeAll = true
                    auth()
                }
            }

            registerButton.setOnClickListener{
                startActivity(intentRegister)
            }

            forgetButton.setOnClickListener {
                startActivity(intentForget)
            }

            validarCorreoButton.setOnClickListener {
                bundleToDialog = Bundle()
                bundleToDialog.putInt("IDUSER", AuxUser)
                Log.i("AUXUSER",AuxUser.toString())
                validarDialog.arguments = bundleToDialog
                validarDialog.show(this.supportFragmentManager,"")
            }

            nombreUsuario.setOnFocusChangeListener { _, hasFocus ->
                if(!hasFocus){
                    val nombre = nombreUsuario.text.toString()
                    if(nombre != "") {
                        isCreate = false
                        validarCorreoUsuario(-10,nombre)
                        Log.i("EXISTEMAIL2: ", ExistEmail.toString())
                    }


                }
            }

        }


        /*userService.getUserById(iduser).enqueue(object:Callback<ResponseUserById>{
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("LOGIN: ","NO HAY CONEXION")
                setContentView(R.layout.activity_log_in)
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful){

                    val res = response.body()


                    if(sharedPreferences.getString("rol",null)=="PACIENTE"){
                        startActivity(intentMainPatient)
                    }else{
                        startActivity(intentMain)
                    }
                }else{
                    Log.i("LOGIN: ","TODAVIA NO INICA SESION")
                    //setContentView(R.layout.activity_log_in)

                }
            }
        })*/


    }

    companion object{
        private var AuxUser = -10
        private var ExistEmail = false
    }

    private fun auth(){
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val authService = retrofit.create<AuthService>(AuthService::class.java)
        val nombreUsuario = findViewById<EditText>(R.id.et_nombreUsuario)
        val contrasena = findViewById<EditText>(R.id.et_contrasena2)

        val intentMain = Intent(this, MainActivity::class.java)
        val intentMainPatient = Intent(this,MainActivityPatient::class.java)

        if(nombreUsuario.text.toString().isEmpty()){
            nombreUsuario.setError("Completar este campo")
            nombreUsuario.requestFocus()
            completeAll = false
        }
        if(contrasena.text.toString().isEmpty()){
            contrasena.setError("Conpletar este campo")
            contrasena.requestFocus()
            completeAll = false
        }
        if(completeAll){
            val authRequest = AuthRequest(nombreUsuario.text.toString(),contrasena.text.toString())
            //val authRequest = Usuario(null,null,null,null,contrasena.toString(),nombreUsuario.toString(),null,null,null)


            authService.authenticate(authRequest).enqueue(object : Callback<AuthResponse>{
                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    Log.i("INICIAR SESION","NO ENTRO")

                }

                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {

                    if(response.isSuccessful){
                        Log.i("Iniciar Sesion: ", response.body().toString())
                        val res = response.body()
                        //val jsonString = Gson().toJson(res)

                        // val _res:Usuario = G

                        if(res?.rol!! == "PACIENTE"){
                            startActivity(intentMainPatient)
                        }
                        else{
                            startActivity(intentMain)
                        }

                        saveData(res?.id!!,res?.nombreUsuario!!,res?.token!!,res?.rol!!,res?.nombre!!,res?.apellido!!)
                        finish()

                    }
                    else{
                        val res = response.errorBody()?.string()
                        val message = JsonParser().parse(res).asJsonObject["message"].asString

                        Toast.makeText(applicationContext,message,Toast.LENGTH_SHORT).show()
                        Log.i("Respuesta: ", "WHAT FUEEE")
                    }
                }

            })
        }

    }

    private fun validarCorreoUsuario(id:Int, nombreusuario:String){

        userService.getValidateEmail(id,nombreusuario).enqueue(object : Callback<ResponseValidationEmail>{
            override fun onFailure(call: Call<ResponseValidationEmail>, t: Throwable) {
                Log.i("EMAIL: ","NO HAY CONEXION")
            }

            override fun onResponse(
                call: Call<ResponseValidationEmail>,
                response: Response<ResponseValidationEmail>
            ) {
                if (response.isSuccessful){
                    Log.i("EMAIL: ","OK")

                    val res = response.body()

                    ExistEmail = res?.emailExist!!
                    Log.i("EXISTEMAIL: ", ExistEmail.toString())
                    AuxUser = res?.id
                    Log.i("ISCREATE2: ", isCreate.toString())

                    if (!isCreate) {
                        val validarCorreoButton = findViewById<Button>(R.id.btn_validarCorreo)

                        if (!ExistEmail) {
                            Toast.makeText(
                                applicationContext,
                                "correo no validado",
                                Toast.LENGTH_SHORT
                            ).show()
                            validarCorreoButton.visibility = View.VISIBLE
                        } else {
                            validarCorreoButton.visibility = View.GONE

                        }
                    }

                }else{
                    Log.i("EMAIL: ","ERROR")
                    val res = response.errorBody()?.string()
                    val message = JsonParser().parse(res).asJsonObject["message"].asString

                    ExistEmail = false

                    if (!isCreate) {
                        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
                    }

                }
            }

        })
    }


    private fun saveData(id:Int,usuario:String,token:String,rol:String,nombre:String,apellido:String){
        val editor: SharedPreferences.Editor= getSharedPreferences("db_auriculoterapia",0).edit()
        editor.putInt("id",id)
        editor.putString("usuario",usuario)
        editor.putString("token",token)
        editor.putString("rol",rol)
        editor.putString("nombre",nombre)
        editor.putString("apellido",apellido)
        editor.putBoolean("ExistEmail", ExistEmail)

        editor.apply()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        finishAffinity()
    }
}
package com.example.android.auriculoterapia_app.fragments.patient

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.cloudinary.android.uploadwidget.model.Media
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.activities.InitialImageActivity
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.constants.ConfigCloudinary
import com.example.android.auriculoterapia_app.models.SolicitudTratamiento
import com.example.android.auriculoterapia_app.services.ResponseUserById
import com.example.android.auriculoterapia_app.services.TreatmentRequestService
import com.example.android.auriculoterapia_app.services.UserService
import com.google.gson.JsonParser
import com.tooltip.Tooltip
import kotlinx.android.synthetic.main.activity_treatment_pacient.*
import kotlinx.coroutines.android.awaitFrame
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.Period
import java.util.*
import java.util.jar.Manifest

class NewTreatmentFragment  : Fragment(){

    var urlImage : String = ""
    var REQUEST_CODE = 11
    val RESULT_CODE =12
    lateinit var filePath :String
    var completeAll: Boolean = true
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_new_treatment, container, false)
        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)

        val userId = sharedPreferences.getInt("id",0)



        //val optionEdad = view.findViewById<Spinner>(R.id.spinner_edad)
        val userService = ApiClient.retrofit().create<UserService>(UserService::class.java)
        val resultEdad = view.findViewById<TextView>(R.id.tv_resultEdad)
        val checkimage = view.findViewById<ImageView>(R.id.iv_checkImage)
        val tooltipOtros = view.findViewById<Button>(R.id.btn_tooltip_otros)

        val tv_imageURL = view.findViewById<TextView>(R.id.tv_urlImage)
        tv_imageURL.visibility = View.INVISIBLE

        checkimage.visibility = View.INVISIBLE

        filePath = ""

        val registerButton = view.findViewById<Button>(R.id.btn_registrarTratamiento)
        val instertImageButton = view.findViewById<Button>(R.id.btn_InsertarImagen)

        val intentInitialImage = Intent(requireContext(),InitialImageActivity::class.java)

        userService.getUserById(userId).enqueue(object : Callback<ResponseUserById>{
            override fun onFailure(call: Call<ResponseUserById>, t: Throwable) {
                Log.i("NewTreatment: ","Fallo edad")
            }

            override fun onResponse(
                call: Call<ResponseUserById>,
                response: Response<ResponseUserById>
            ) {
                if(response.isSuccessful){
                    val res = response.body()
                    resultEdad.text = res?.edad.toString()
                }
            }

        })


        registerButton.setOnClickListener {
            completeAll = true
            val peso = requireView().findViewById<EditText>(R.id.et_peso)
            val altura = requireView().findViewById<EditText>(R.id.et_altura)
            val sintomas = requireView().findViewById<EditText>(R.id.et_Sintomas)
            val otros = requireView().findViewById<EditText>(R.id.et_otros)
            val imagenAreaAfectada = requireView().findViewById<TextView>(R.id.tv_urlImage)
            val edad = requireView().findViewById<TextView>(R.id.tv_resultEdad)


            if(peso.text.isEmpty() || altura.text.isEmpty() || sintomas.text.isEmpty() || edad.text.isEmpty() || imagenAreaAfectada!!.text.isEmpty()){
                Toast.makeText(requireContext(),"Por favor complete todos los campos",Toast.LENGTH_SHORT).show()
                completeAll = false
            }

            if(filePath!="" && completeAll){
                uploadToCloudinary(filePath)
            }
            else{
                if(completeAll) {
                    Toast.makeText(
                        requireContext(),
                        "No ha registrado la imagen",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }

        instertImageButton.setOnClickListener {
            //check runtime permission
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if(this.requireActivity().checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE)==
                        PackageManager.PERMISSION_DENIED){
                    //permision denied
                    val permissions = arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE);
                    //show popup
                    requestPermissions(permissions, PERMISSION_CODE);
                }else{
                    //permission granted
                    startActivityForResult(intentInitialImage,REQUEST_CODE)
                }
            }else{
                //system os is < Marshmellow
                startActivityForResult(intentInitialImage,REQUEST_CODE)
            }
        }
        tooltipOtros.setOnClickListener {
            val tooltip = Tooltip.Builder(tooltipOtros)
                .setText("¿Que otros sintomas tiene?")
                .setTextColor(Color.WHITE)
                .setGravity(Gravity.END)
                .setCornerRadius(8f)
                .setDismissOnClick(true)

            tooltip.show()
        }



        return view
    }

    companion object{
        //image pick code
        private val IMAGE_PICK_CODE = 1000;
        //permision code
        private val PERMISSION_CODE =1001;

        private var urlImage2 = ""
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            PERMISSION_CODE ->{
                if(grantResults.size>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                    //permision from popup granted
                    val intentInitialImage = Intent(requireContext(),InitialImageActivity::class.java)
                    startActivity(intentInitialImage)
                }else{
                    Toast.makeText(requireContext(),"Permiso denegado",Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun registrarTratamiento(){
        val treatmentRequestService = ApiClient.retrofit().create(TreatmentRequestService::class.java)
        val sharedPreferences = this.requireActivity().getSharedPreferences("db_auriculoterapia",0)
        val token = sharedPreferences.getString("token","")
        val userId = sharedPreferences.getInt("id",0)
        //val intentContinueTreatment = Intent(requireContext(),EvaluationFormTreatmentFragment::class.java)

        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val dateInString = formatter.format(date)



        val peso = requireView().findViewById<EditText>(R.id.et_peso)
        val altura = requireView().findViewById<EditText>(R.id.et_altura)
        val sintomas = requireView().findViewById<EditText>(R.id.et_Sintomas)
        val otros = requireView().findViewById<EditText>(R.id.et_otros)
        val imagenAreaAfectada = requireView().findViewById<TextView>(R.id.tv_urlImage)
        val edad = requireView().findViewById<TextView>(R.id.tv_resultEdad)


        if(peso.text.isEmpty() || altura.text.isEmpty() || sintomas.text.isEmpty() || edad.text.isEmpty() || imagenAreaAfectada!!.text.isEmpty()){
            Toast.makeText(requireContext(),"Por favor complete todos los campos",Toast.LENGTH_SHORT).show()
            completeAll = false
        }else{
            if(peso.text.toString().toDouble() < 20 || peso.text.toString().toDouble() > 200){
                peso.setError("El peso debe estar dentro de las 20.0 y 200.0 kg")
                peso.setText("")
                peso.requestFocus()
                completeAll = false
            }

            if(altura.text.toString().toDouble() < 1 || altura.text.toString().toDouble() > 2.10){
                altura.setError("La altura debe estarentre 1.00 y 2.10 m")
                altura.setText("")
                altura.requestFocus()
                completeAll = false
            }

            if(sintomas.text.toString().length < 5 || sintomas.text.toString().length > 30){
                sintomas.setError("Máximo 30 caracteres")
                sintomas.requestFocus()
                completeAll = false
            }
        }



        if(completeAll){
            val solicitudTratamiento = SolicitudTratamiento(altura.text.toString().toDouble(),
                edad.text.toString().toInt(),null, imagenAreaAfectada.text.toString(),otros.text.toString(),null,
                peso.text.toString().toDouble(),sintomas.text.toString(),
                dateInString,"En proceso",null)

            Log.i("UserId: ","$userId")

            treatmentRequestService.registerTreatment("Bearer $token",userId,solicitudTratamiento)
                .enqueue(object : Callback<SolicitudTratamiento>{
                    override fun onFailure(call: Call<SolicitudTratamiento>, t: Throwable) {
                        Log.i("REGISTRAR TRATAMIENTO","GG WP")
                    }

                    override fun onResponse(
                        call: Call<SolicitudTratamiento>,
                        response: Response<SolicitudTratamiento>
                    ) {
                        if(response.isSuccessful){
                            Log.i("REGISTRAR TRATAMIENTO: ", response.body().toString())
                            Toast.makeText(requireContext(),"Se registró el tratamiento correctamente",Toast.LENGTH_SHORT).show()
                            activity?.finish() //REVISAR
                            //fragmentTreatmentContainer.
                            //startActivity(intentContinueTreatment)
                        }
                        else{
                            when(response.code()){
                                400 ->{
                                    val res = response.errorBody()?.string()
                                    val message = JsonParser().parse(res).asJsonObject["message"].asString

                                    Toast.makeText(requireContext(),message,Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                    }

                })
        }


    }

     private fun uploadToCloudinary(filename : String){
        Log.i("UPLOAD: ","UPLOAD TO CLOUDINARY")
         val tv_imageURL = requireView().findViewById<TextView>(R.id.tv_urlImage)
         //tv_imageURL.visibility = View.GONE
         //urlImage=""
        MediaManager.get().upload(filename).callback(object: UploadCallback {
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                //Toast.makeText(requireContext(), resultData?.get("url").toString(),Toast.LENGTH_LONG).show()
                tv_imageURL!!.text = resultData?.get("url").toString()
                registrarTratamiento()
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                Log.i("Cargando: ", "Subiendo Imagen")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                Log.i("Error: ", error!!.description)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.i("Error: ", error!!.description)
            }

            override fun onStart(requestId: String?) {
                Log.i("START: ", "empezando")
            }

        }).dispatch()

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode == REQUEST_CODE && resultCode == RESULT_CODE){
            val result = data?.getStringExtra("filepath")
            filePath = result.toString()

            //val config = ConfigCloudinary.config()
            //MediaManager.init(requireContext(),config) //problema se vuelve a generar



            val checkimage = requireView().findViewById<ImageView>(R.id.iv_checkImage)
            checkimage!!.visibility = View.VISIBLE

        }
        else{
            Toast.makeText(requireContext(),"No se registró la imagen, volver a intentar",Toast.LENGTH_LONG).show()
            completeAll = false
        }
    }


}
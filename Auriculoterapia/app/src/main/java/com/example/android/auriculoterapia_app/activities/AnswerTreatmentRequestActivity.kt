package com.example.android.auriculoterapia_app.activities

import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import com.example.android.auriculoterapia_app.R
import com.example.android.auriculoterapia_app.constants.ApiClient
import com.example.android.auriculoterapia_app.models.helpers.FormularioTratamiento
import com.example.android.auriculoterapia_app.services.TreatmentService
import com.example.android.auriculoterapia_app.util.DrawableImageView
import com.example.android.auriculoterapia_app.util.ListaTiposDeTratamiento
import com.google.android.material.datepicker.*
import org.w3c.dom.Text
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*

class AnswerTreatmentRequestActivity : AppCompatActivity() {
    lateinit var imagenAEditar: DrawableImageView
    lateinit var botonLimpiar: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_answer_treatment_request)

        //Elementos de UI
        val selectorTipoTratamiento = findViewById<Spinner>(R.id.spinner_tipo_tratamiento)
        val botonFecha = findViewById<ImageButton>(R.id.btn_rango_fecha_tratamiento)
        val frecuenciaEditText = findViewById<EditText>(R.id.frecuencia_edit_text)
        val tiempoTerapiaEditText = findViewById<EditText>(R.id.tiempo_terapia_edit_text)
        val startDateText = findViewById<TextView>(R.id.startDateTreatment)
        val endDateText = findViewById<TextView>(R.id.endDateTreatment)
        val fechaEnvioText = findViewById<TextView>(R.id.fechaEnvioRespuestaTratamiento)
        val textoPuntos = findViewById<TextView>(R.id.textoPuntos)

        val botonEnviar = findViewById<Button>(R.id.saveFormTreatment)

        val errorSpinnerTratamiento = findViewById<TextView>(R.id.respuesta_error_tratamiento_no_seleccionado)
        val errorFechas = findViewById<TextView>(R.id.respuesta_error_fechas_no_seleccionadas)





        ///////////Material Date Picker///////////

        ////VALIDAR QUE SEA MÁXIMO DE UNA SEMANA

        startDateText.text = "____-__-__"
        endDateText.text = "____-__-__"
        //DatePickerBuilder
        val builder = MaterialDatePicker.Builder.dateRangePicker()

        val now = Calendar.getInstance()
        now.add(Calendar.DATE, -1)
        val min = now.time
        now.add(Calendar.DATE, 1)
        val max = now.time
        now.add(Calendar.DATE, -1)
        builder.setSelection(androidx.core.util.Pair(min.time, max.time))
        builder.setTheme(R.style.ThemeOverlay_MaterialComponents_MaterialCalendar)
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        builder.setTitleText("Selecciona un rango de fechas")
        //CONSTRAINTS
        val constraintsBuilderRange = CalendarConstraints.Builder()


        //calendar.add(Calendar.DATE, -1)

        val dateValidatorMin = DateValidatorPointForward.now()
        //val minDate = calendar.time
        now.add(Calendar.DATE, 8)
        val calendar = now.time
        //val maxDate = calendar.time
        val dateValidatorMax = DateValidatorPointBackward.before(calendar.time)

        val listValidators = arrayListOf<CalendarConstraints.DateValidator>(
            dateValidatorMin, dateValidatorMax
        )

        val validators = CompositeDateValidator.allOf(listValidators)
        constraintsBuilderRange.setValidator(validators)

        builder.setCalendarConstraints(constraintsBuilderRange.build())
        var picker = builder.build()


        //DatePicker
        botonFecha.setOnClickListener{
            picker.show(supportFragmentManager, picker.toString())
            picker.addOnNegativeButtonClickListener{
                picker.dismiss()
            }
            picker.addOnPositiveButtonClickListener {
                val timeZoneUTC = TimeZone.getDefault()
                val offsetFromUTC: Int = timeZoneUTC.getOffset(Date().time) * -1
                startDateText.text = formatter.format(it.first?.plus(offsetFromUTC))
                endDateText.text = formatter.format(it.second?.plus(offsetFromUTC))
                errorFechas.visibility = View.GONE
            }

        }

        /// SETTING THE APPOINTMENT ID
        var solicitudTratamientoId = 0
        var imagenUrl = ""
        var pacienteId = 0
        var nombrePaciente = ""
        var apellidoPaciente = ""

        intent.extras?.let{
            val bundle: Bundle = it
            solicitudTratamientoId = bundle.getInt("solicitudTratamientoId")
            imagenUrl = bundle.getString("imageUrl").toString()
            pacienteId = bundle.getInt("pacienteId")
            nombrePaciente = bundle.getString("nombrePaciente").toString()
            apellidoPaciente = bundle.getString("apellidoPaciente").toString()

        }

        imagenAEditar = findViewById(R.id.toEditPhoto)
        imagenAEditar.setImagenUrl(imagenUrl)
        botonLimpiar = findViewById(R.id.clearPoints)

        botonLimpiar.setOnClickListener{
            imagenAEditar.startNew()
        }

        /////////////////////////////////////////

        /// COMBO BOX TIPO TRATAMIENTO
        var textoTipoTratamiento: String = ""
        val listaDeTratamientos =  ListaTiposDeTratamiento.lista

        selectorTipoTratamiento.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listaDeTratamientos)
        selectorTipoTratamiento.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                textoTipoTratamiento = listaDeTratamientos.get(0)
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?,
                                        position: Int, id: Long) {
                errorSpinnerTratamiento.visibility = View.GONE
                textoTipoTratamiento = listaDeTratamientos.get(position)
            }
        }
        /////////////////////////////////////////

        /////SETTING PUBLICATION DATE
       fechaEnvioText.text = formatter.format(Calendar.getInstance().time)
        /////////////////////////////////////////
        val alertBuilder = AlertDialog.Builder(this)
       botonEnviar.setOnClickListener{

            if(!(startDateText.text == "____-__-__") &&
                !(endDateText.text == "____-__-__") &&
                !frecuenciaEditText.text.isEmpty() &&
                !tiempoTerapiaEditText.text.isEmpty() &&
                !(textoTipoTratamiento.equals(listaDeTratamientos.get(0)))
                        && imagenAEditar.getCantidadPuntos() > 0
            ) {


                val form = FormularioTratamiento(textoTipoTratamiento,
                    fechaEnvioText.text.toString(),
                    startDateText.text.toString(),
                    endDateText.text.toString(),
                    Integer.parseInt(frecuenciaEditText.text.toString()),
                    Integer.parseInt(tiempoTerapiaEditText.text.toString()),
                    "asdgsdgdgds.jpg",
                    solicitudTratamientoId
                )

                textoPuntos.setError(null)
                alertBuilder.setMessage(R.string.confirmacion_envio_tratamiento)
                    .setPositiveButton("Enviar",
                        DialogInterface.OnClickListener { dialog, id ->
                            uploadToCloudinaryAndRegisterTreatment(form, imagenAEditar.overlay()!!, pacienteId, nombrePaciente, apellidoPaciente)

                        })
                    .setNegativeButton("Cancelar",
                        DialogInterface.OnClickListener { dialog, id ->
                            dialog.dismiss()
                        })
                // Create the AlertDialog object and return it
                val dialog = alertBuilder.create()
                dialog.show()


                Log.i("Tratamiento", form.toString())

            } else{
                if(textoTipoTratamiento.equals(listaDeTratamientos.get(0))){
                    errorSpinnerTratamiento.visibility = View.VISIBLE
                    errorSpinnerTratamiento.setError("Debes seleccionar el tratamiento")
                }
                if (startDateText.text == "____-__-__" || endDateText.text == "____-__-__"){
                    errorFechas.visibility = View.VISIBLE
                    errorFechas.setError("Debes ingresar el rango de fechas")
                }
                if(frecuenciaEditText.text.isEmpty()){
                    frecuenciaEditText.setError("Debes seleccionar la frecuencia diaria")
                }
                if(tiempoTerapiaEditText.text.isEmpty()){
                    tiempoTerapiaEditText.setError("Debe seleccionar el tiempo de\ncada terapia")
                }
                if(imagenAEditar.getCantidadPuntos() <= 0){
                    Log.i("Cantidad de puntos", imagenAEditar.getCantidadPuntos().toString())
                    textoPuntos.setError("Debe dibujar los puntos para la terapia")
                }

            }


        }

    }



    fun uploadToCloudinaryAndRegisterTreatment(form: FormularioTratamiento, bitmap: Bitmap, pacienteId: Int,
                                               nombrePaciente:String, apellidoPaciente: String){
        val byteArrayOfBitmap = convertirBitmapAByteArray(bitmap)
        MediaManager.get().upload(byteArrayOfBitmap).callback(object: UploadCallback {
            override fun onSuccess(requestId: String?, resultData: MutableMap<Any?, Any?>?) {
                form.imagenEditada = resultData?.get("url").toString()
                registrarTratamiento(form)
                Toast.makeText(this@AnswerTreatmentRequestActivity, "Registro de tratamiento exitoso", Toast.LENGTH_SHORT).show()
                val intent = Intent(this@AnswerTreatmentRequestActivity, HistoryActivity::class.java)
                intent.putExtra("pacienteId", pacienteId)
                intent.putExtra("nombrePaciente", nombrePaciente)
                intent.putExtra("apellidoPaciente", apellidoPaciente)
                startActivity(intent)
                finish()
            }

            override fun onProgress(requestId: String?, bytes: Long, totalBytes: Long) {
                Log.i("onProgress: ", "Subiendo Imagen")
            }

            override fun onReschedule(requestId: String?, error: ErrorInfo?) {
                Log.i("onReschedule: ", error!!.description)
            }

            override fun onError(requestId: String?, error: ErrorInfo?) {
                Log.i("onError: ", error!!.description)
                Toast.makeText(this@AnswerTreatmentRequestActivity, "No se registró la imagen, vuelva a intentar", Toast.LENGTH_SHORT).show()
            }

            override fun onStart(requestId: String?) {
                Log.i("START: ", "empezando")
            }
        }).dispatch()


    }

    fun registrarTratamiento(form: FormularioTratamiento){
        val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)
        tratamientoService.registerTreatment(form).enqueue(object: Callback<Boolean>{
            override fun onFailure(call: Call<Boolean>, t: Throwable) {
                Toast.makeText(applicationContext, "Fallo en el envío de tratamiento", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {

            }
        })
    }


    fun convertirBitmapAByteArray(bitmap: Bitmap): ByteArray{
        val baos = ByteArrayOutputStream()
        try{
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, baos)
            return baos.toByteArray()

        }finally {
            try{
                baos.close()
            } catch(e: Exception){
                e.printStackTrace()
            }
        }

    }


}


/*fun registrarRepuestaTratamiento(body: FormularioTratamiento){
    val tratamientoService = ApiClient.retrofit().create(TreatmentService::class.java)

    tratamientoService.registerTreatment(body).enqueue(object: Callback<Boolean>{
        override fun onFailure(call: Call<Boolean>, t: Throwable) {
            Toast.makeText(this@AnswerTreatmentRequestActivity, "Fallo", Toast.LENGTH_SHORT).show()
        }

        override fun onResponse(call: Call<Boolean>, response: Response<Boolean>) {
             if(response.isSuccessful) {

                 Toast.makeText(this@AnswerTreatmentRequestActivity, "Tratamiento enviado", Toast.LENGTH_SHORT).show()

             }
        }

    }

    )


}*/
/*INTENT DE EMERGENCIA A EDIT PHOTO
*
*    /*val intent = Intent(this, EditPhotoFromRequestActivity::class.java)
                intent.putExtra("solicitudTratamientoId", solicitudTratamientoId)
                intent.putExtra("imagenUrl", imagenUrl)
                intent.putExtra("formTratamiento", body)
                intent.putExtra("pacienteId", pacienteId)
                intent.putExtra("nombrePaciente", nombrePaciente)
                intent.putExtra("apellidoPaciente", apellidoPaciente)
                startActivity(intent)*/
* */
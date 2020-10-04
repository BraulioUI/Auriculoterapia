package com.example.android.auriculoterapia_app.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.android.auriculoterapia_app.R


class ImcDetailObesity: AppCompatDialogFragment() {

    private lateinit var delgadezMuySevera: TextView
    private lateinit var delgadezSevera: TextView
    private lateinit var delgadez: TextView
    private lateinit var pesoSaludable: TextView
    private lateinit var sobrepeso: TextView
    private lateinit var obesidadSevera: TextView
    private lateinit var obesidadMorbida: TextView

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        val inflaterImc = requireActivity().layoutInflater

        val viewImc = inflaterImc.inflate(R.layout.layout_imc_detail_dialog, null)

        val colorFactory = ColorIndicatorFactory(requireContext())

        delgadezMuySevera = viewImc.findViewById(R.id.imcDelgadezMuySevera)
        delgadezSevera = viewImc.findViewById(R.id.imcDelgadezSevera)
        delgadez = viewImc.findViewById(R.id.imcDelgadez)
        pesoSaludable = viewImc.findViewById(R.id.imcPesoSaludable)
        sobrepeso = viewImc.findViewById(R.id.imcSobrepeso)
        obesidadSevera = viewImc.findViewById(R.id.imcObesidadSevera)
        obesidadMorbida = viewImc.findViewById(R.id.imcObesidadMorbida)

        val circulo1 = delgadezMuySevera.background as GradientDrawable
        circulo1.setColor(colorFactory.obtenerColorIMC("Delgadez muy severa"))

        val circulo2 = delgadezSevera.background as GradientDrawable
        circulo2.setColor(colorFactory.obtenerColorIMC("Delgadez severa"))

        val circulo3 = delgadez.background as GradientDrawable
        circulo3.setColor(colorFactory.obtenerColorIMC("Delgadez"))

        val circulo4 = pesoSaludable.background as GradientDrawable
        circulo4.setColor(colorFactory.obtenerColorIMC("Peso saludable"))

        val circulo5 = sobrepeso.background as GradientDrawable
        circulo5.setColor(colorFactory.obtenerColorIMC("Sobrepeso"))

        val circulo6 = obesidadSevera.background as GradientDrawable
        circulo6.setColor(colorFactory.obtenerColorIMC("Obesidad severa"))

        val circulo7 = obesidadMorbida.background as GradientDrawable
        circulo7.setColor(colorFactory.obtenerColorIMC("Obesidad mórbida"))

        builder.setView(viewImc).setTitle("Leyenda (IMC)")
            .setNegativeButton("Cerrar", DialogInterface.OnClickListener{
                    dialog, id ->
                dialog.dismiss()
            })

        val dialog = builder.create()
        return dialog
    }

}
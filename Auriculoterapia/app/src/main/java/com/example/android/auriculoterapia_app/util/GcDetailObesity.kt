package com.example.android.auriculoterapia_app.util

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.android.auriculoterapia_app.R


class GcDetailObesity: AppCompatDialogFragment() {



    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(requireActivity())
        val inflaterImc = requireActivity().layoutInflater

        val viewImc = inflaterImc.inflate(R.layout.layout_gc_detail_dialog, null)

        val colorFactory = ColorIndicatorFactory(requireContext())

        val colorBueno = colorFactory.obtenerColorGC("BUENA")
        val colorNormal = colorFactory.obtenerColorGC("NORMAL")
        val colorElevado = colorFactory.obtenerColorGC("ELEVADA")
        val colorMuyElevado = colorFactory.obtenerColorGC("MUY ELEVADA")

        val masculinoBueno = viewImc.findViewById<TextView>(R.id.masculinoBueno)
        val masculinoNormal = viewImc.findViewById<TextView>(R.id.masculinoNormal)
        val masculinoElevado = viewImc.findViewById<TextView>(R.id.masculinoElevado)
        val masculinoMuyElevado = viewImc.findViewById<TextView>(R.id.masculinoMuyElevado)

        val femeninoBueno = viewImc.findViewById<TextView>(R.id.femeninoBueno)
        val femeninoNormal = viewImc.findViewById<TextView>(R.id.femeninoNormal)
        val femeninoElevado = viewImc.findViewById<TextView>(R.id.femeninoElevado)
        val femeninoMuyElevado = viewImc.findViewById<TextView>(R.id.femeninoMuyElevado)



        val circuloBuenoMasc = masculinoBueno.background as GradientDrawable
        circuloBuenoMasc.setColor(colorBueno)
        val circuloNormalMasc = masculinoNormal.background as GradientDrawable
        circuloNormalMasc.setColor(colorNormal)
        val circuloElevadoMasc = masculinoElevado.background as GradientDrawable
        circuloElevadoMasc.setColor(colorElevado)
        val circuloMuyElevadoMasc = masculinoMuyElevado.background as GradientDrawable
        circuloMuyElevadoMasc.setColor(colorMuyElevado)


        val circuloBuenoFem = femeninoBueno.background as GradientDrawable
        circuloBuenoFem.setColor(colorBueno)
        val circuloNormalFem = femeninoNormal.background as GradientDrawable
        circuloNormalFem.setColor(colorNormal)
        val circuloElevadoFem = femeninoElevado.background as GradientDrawable
        circuloElevadoFem.setColor(colorElevado)
        val circuloMuyElevadoFem = femeninoMuyElevado.background as GradientDrawable
        circuloMuyElevadoFem.setColor(colorMuyElevado)

        builder.setView(viewImc).setTitle("Leyenda (IMC)")
            .setNegativeButton("Cerrar", DialogInterface.OnClickListener{
                    dialog, id ->
                dialog.dismiss()
            })

        val dialog = builder.create()
        return dialog
    }

}
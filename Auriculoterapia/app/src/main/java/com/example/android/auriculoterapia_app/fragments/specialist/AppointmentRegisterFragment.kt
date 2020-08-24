package com.example.android.auriculoterapia_app.fragments.specialist

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.android.auriculoterapia_app.R
import kotlinx.android.synthetic.main.fragment_appointment_register.*
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.*


class AppointmentRegisterFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_appointment_register, container, false)

        //Spinner actions

        val selectorPacientes = view.findViewById<Spinner>(R.id.patientSpinner)

        val options = arrayOf<String>("César Pizarro Llanos", "Braulio Uribe Iraola")

        selectorPacientes.adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, options)

        selectorPacientes.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?,view: View?,
                position: Int,id: Long) {
                print(options.get(position))
            }
        }

        //DatePicker actions
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)

        val dateEditText = view.findViewById<TextView>(R.id.dateEditText)
        val date = Calendar.getInstance().time
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val dateInString = formatter.format(date)
        dateEditText.text = dateInString

        dateEditText.setOnClickListener{
            val dpd = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener{
                view, mYear, mMonth, mDay -> dateEditText.text = "$mYear-${mMonth + 1}-$mDay"
            }, year, month, day
            )

            dpd.show()
        }


        return view;
    }

}
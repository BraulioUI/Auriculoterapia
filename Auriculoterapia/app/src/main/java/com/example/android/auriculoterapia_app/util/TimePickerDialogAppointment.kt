package com.example.android.auriculoterapia_app.util

import android.app.TimePickerDialog
import android.content.Context
import android.widget.TimePicker


class TimePickerDialogAppointment: TimePickerDialog {

    private val TIME_PICKER_INTERVAL = 30
    private lateinit var mTimePicker: TimePicker
    private lateinit var mTimeSetListener: TimePickerDialog.OnTimeSetListener

    constructor(
        context: Context,
        timeSetListener: TimePickerDialog.OnTimeSetListener,
        hourOfDay: Int,
        minute: Int,
        is24HourView: Boolean

    ): super(context,TimePickerDialog.THEME_HOLO_LIGHT, timeSetListener,hourOfDay,minute,is24HourView){

    }
    override fun onTimeChanged(view: TimePicker?, hourOfDay: Int, minute: Int) {
        super.onTimeChanged(view, hourOfDay, minute)

    }


}






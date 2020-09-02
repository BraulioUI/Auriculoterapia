package com.example.android.auriculoterapia_app.util

import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.view.View
import android.widget.NumberPicker
import android.widget.TimePicker
import java.util.*

class CustomTimePickerDialog(
    context: Context?, private val mTimeSetListener: OnTimeSetListener?,
    hourOfDay: Int, minute: Int, is24HourView: Boolean
) : TimePickerDialog(
    context, THEME_HOLO_LIGHT, null, hourOfDay,
    minute / TIME_PICKER_INTERVAL, is24HourView
) {

    companion object {
        private const val TIME_PICKER_INTERVAL = 30
    }

    private var mTimePicker: TimePicker? = null
    override fun updateTime(hourOfDay: Int, minuteOfHour: Int) {
        mTimePicker!!.currentHour = hourOfDay
        mTimePicker!!.currentMinute = minuteOfHour / TIME_PICKER_INTERVAL
    }

    override fun onClick(dialog: DialogInterface, which: Int) {
        when (which) {
            DialogInterface.BUTTON_POSITIVE -> mTimeSetListener?.onTimeSet(
                mTimePicker, mTimePicker!!.currentHour,
                mTimePicker!!.currentMinute * TIME_PICKER_INTERVAL
            )
            DialogInterface.BUTTON_NEGATIVE -> cancel()
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        try {
            val classForid =
                Class.forName("com.android.internal.R\$id")
            val timePickerField = classForid.getField("timePicker")
            mTimePicker =
                findViewById<View>(timePickerField.getInt(null)) as TimePicker
            val minuteField = classForid.getField("minute")
            val minuteSpinner = mTimePicker!!.findViewById<View>(minuteField.getInt(null)) as NumberPicker
/*
            val hourField = classForid.getField("hour")
            val hourSpinner = mTimePicker!!.findViewById<View>(hourField.getInt(null)) as NumberPicker
*/
            minuteSpinner.minValue = 0
            minuteSpinner.maxValue = 60 / TIME_PICKER_INTERVAL - 1
/*
            hourSpinner.minValue = 9
            hourSpinner.maxValue = 18*/

            val displayedValues: MutableList<String> =
                ArrayList()
            var i = 0
            while (i < 60) {
                displayedValues.add(String.format("%02d", i))
                i += TIME_PICKER_INTERVAL
            }


            minuteSpinner.displayedValues = displayedValues
                .toTypedArray()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }


}
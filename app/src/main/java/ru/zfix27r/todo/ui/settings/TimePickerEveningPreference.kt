package ru.zfix27r.todo.ui.settings

import android.content.Context
import android.content.SharedPreferences
import android.text.format.DateFormat.is24HourFormat
import android.util.AttributeSet
import android.view.View
import android.widget.TextView
import androidx.preference.Preference
import androidx.preference.PreferenceManager
import androidx.preference.PreferenceViewHolder
import com.google.android.material.timepicker.MaterialTimePicker
import com.google.android.material.timepicker.TimeFormat
import ru.zfix27r.todo.R
import ru.zfix27r.todo.ui.MainActivity
import java.time.LocalTime

class TimePickerEveningPreference(
    context: Context, attrs: AttributeSet?
) : TimePickerPreference(context, attrs) {
    override val reminderHourKey = context.getString(R.string.reminder_evening_hour_key)
    override val reminderHourDefaultValue =
        context.getString(R.string.reminder_evening_hour_default_value).toInt()
    override val reminderMinuteKey = context.getString(R.string.reminder_evening_minute_key)
    override val reminderMinuteDefaultValue =
        context.getString(R.string.reminder_evening_minute_default_value).toInt()
}
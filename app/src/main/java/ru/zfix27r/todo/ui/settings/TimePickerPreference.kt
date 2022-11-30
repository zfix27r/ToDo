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

abstract class TimePickerPreference(context: Context, attrs: AttributeSet?) :
    Preference(context, attrs),
    View.OnClickListener {
    private val sharedPrefs: SharedPreferences =
        PreferenceManager.getDefaultSharedPreferences(context)

    abstract val reminderHourKey: String
    abstract val reminderHourDefaultValue: Int
    abstract val reminderMinuteKey: String
    abstract val reminderMinuteDefaultValue: Int

    private lateinit var tv: TextView

    override fun onBindViewHolder(holder: PreferenceViewHolder) {
        super.onBindViewHolder(holder)

        val time = getTime()

        tv = holder.findViewById(R.id.time) as TextView
        tv.text = time.toString()
        holder.itemView.setOnClickListener(this)
    }

    override fun onClick(v: View) {
        val isSystem24Hour = is24HourFormat(context)
        val clockFormat = if (isSystem24Hour) TimeFormat.CLOCK_24H else TimeFormat.CLOCK_12H

        val time = getTime()

        val picker =
            MaterialTimePicker.Builder()
                .setTimeFormat(clockFormat)
                .setHour(time.hour)
                .setMinute(time.minute)
                .build()

        picker.addOnPositiveButtonClickListener {
            val editor = sharedPrefs.edit()
            editor.putInt(reminderHourKey, picker.hour)
            editor.putInt(reminderMinuteKey, picker.minute)
            editor.apply()

            val currentTime = LocalTime.of(picker.hour, picker.minute)
            tv.text = currentTime.toString()
        }

        picker.show((context as MainActivity).supportFragmentManager, REMINDER_TAG)
    }

    private fun getTime(): LocalTime {
        val hour = sharedPrefs.getInt(reminderHourKey, reminderHourDefaultValue)
        val minute = sharedPrefs.getInt(reminderMinuteKey, reminderMinuteDefaultValue)
        return LocalTime.of(hour, minute)
    }

    companion object {
        const val REMINDER_TAG = "reminder_tag"
    }
}
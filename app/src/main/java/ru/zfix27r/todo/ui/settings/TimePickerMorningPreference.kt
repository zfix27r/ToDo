package ru.zfix27r.todo.ui.settings

import android.content.Context
import android.util.AttributeSet
import ru.zfix27r.todo.R

class TimePickerMorningPreference(
    context: Context, attrs: AttributeSet?
) : TimePickerPreference(context, attrs) {
    override val reminderHourKey = context.getString(R.string.reminder_morning_hour_key)
    override val reminderHourDefaultValue =
        context.getString(R.string.reminder_morning_hour_default_value).toInt()
    override val reminderMinuteKey = context.getString(R.string.reminder_morning_minute_key)
    override val reminderMinuteDefaultValue =
        context.getString(R.string.reminder_morning_minute_default_value).toInt()
}
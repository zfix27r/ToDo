package ru.zfix27r.todo.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

object BindingAdapters {
    const val DATE_PATTERN_SAVE = "yyyy-MM-dd HH:mm"
    private const val DATE_PATTERN_DAY = "k:mm"
    private const val DATE_PATTERN_WEEK = "E k:mm"
    private const val DATE_PATTERN_MONTH = "LLL d"
    private const val DATE_PATTERN_YEAR = "y LLL"

    @JvmStatic
    @BindingAdapter("bindFormatDate")
    fun bindFormatDate(textView: TextView, date: String?) {
        date?.let {
            val formatterSave = DateTimeFormatter.ofPattern(DATE_PATTERN_SAVE)
            val save = LocalDateTime.parse(date, formatterSave)
            val current = LocalDateTime.now()

            textView.text =
                if (current.minusDays(1L).isBefore(save))
                    getFormatDate(save, DATE_PATTERN_DAY)
                else if (current.minusWeeks(1L).isBefore(save))
                    getFormatDate(save, DATE_PATTERN_WEEK)
                else if (current.minusYears(1L).isBefore(save))
                    getFormatDate(save, DATE_PATTERN_MONTH)
                else
                    getFormatDate(save, DATE_PATTERN_YEAR)
        }
    }

    private fun getFormatDate(date: LocalDateTime, format: String): String {
        return date.format(DateTimeFormatter.ofPattern(format)).toString()
    }
}
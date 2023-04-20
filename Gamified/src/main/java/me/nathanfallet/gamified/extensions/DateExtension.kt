package me.nathanfallet.gamified.extensions

import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

val previous17Weeks: Date
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, -118)
        return calendar.time
    }

val previous7Weeks: Date
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, -48)
        return calendar.time
    }

val previous4Weeks: Date
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, -27)
        return calendar.time
    }

val nextYear: Date
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = Date()
        calendar.add(Calendar.DATE, 365)
        return calendar.time
    }

// String for date

val Date.asString: String
    get() {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return formatter.format(this)
    }

// Date enumeration

val Date.nextStartOfWeek: Date
    get() {
        val calendar = Calendar.getInstance()
        calendar.time = this
        while (calendar.get(Calendar.DAY_OF_WEEK) != calendar.firstDayOfWeek) {
            calendar.add(Calendar.DATE, 1)
        }
        return calendar.time
    }

fun Date.getDays(until: Date): List<Date> {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.set(Calendar.HOUR, 0)
    calendar.set(Calendar.MINUTE, 0)
    calendar.set(Calendar.SECOND, 0)
    val result = ArrayList<Date>()

    while (calendar.time < until) {
        result.add(calendar.time)
        calendar.add(Calendar.DATE, 1)
    }

    return result
}

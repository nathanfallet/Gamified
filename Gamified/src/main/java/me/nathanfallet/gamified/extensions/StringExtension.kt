package me.nathanfallet.gamified.extensions

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

val String.asDate: Date?
    get() {
        val formatter = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        return formatter.parse(this)
    }
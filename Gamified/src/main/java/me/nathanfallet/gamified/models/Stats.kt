package me.nathanfallet.gamified.models

import java.util.Date

data class Stats(
    /**
     * The day of the entry
     */
    val day: Date,

    /**
     * The value of the entry
     */
    val value: Long
)

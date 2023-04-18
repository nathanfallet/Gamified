package me.nathanfallet.gamified.models

data class Counter(
    /**
     * Icon shown in the counter
     */
    var icon: String,

    /**
     * Text shown in the counter
     */
    var text: String,

    /**
     * Value of the counter
     */
    var count: Long
)

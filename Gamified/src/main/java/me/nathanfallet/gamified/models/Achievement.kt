package me.nathanfallet.gamified.models

data class Achievement(
    /**
     * Key of the achievement
     */
    var key: String,

    /**
     * Icon shown in the achievement
     */
    var icon: String,

    /**
     * Text shown in the achievement
     */
    var text: String,

    /**
     * The value of the achievement
     */
    var value: Long,

    /**
     * The target value of the achievement
     */
    var target: Long
) {

    /**
     * Whether the achievement is unlocked
     */
    val unlocked: Boolean
        get() = value >= target

}

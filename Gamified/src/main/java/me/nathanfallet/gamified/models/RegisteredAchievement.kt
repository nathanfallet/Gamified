package me.nathanfallet.gamified.models

data class RegisteredAchievement(
    /**
     * Key of the achievement
     */
    val key: String,

    /**
     * Name of the achievement
     */
    val name: String,

    /**
     * Icon shown in the achievement
     */
    val icon: String,

    /**
     * Target value to unlock this achievement
     */
    val target: Long,

    /**
     * Experience given when achieved
     */
    val experience: Long
)

package me.nathanfallet.gamified.events

import me.nathanfallet.gamified.models.RegisteredAchievement

data class AchievementUnlockedEvent(
    val achievement: RegisteredAchievement
)

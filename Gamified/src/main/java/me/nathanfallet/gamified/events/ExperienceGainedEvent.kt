package me.nathanfallet.gamified.events

import me.nathanfallet.gamified.models.Experience

data class ExperienceGainedEvent(
    val previousExperience: Experience,
    val newExperience: Experience
)

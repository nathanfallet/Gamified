package me.nathanfallet.gamified.models

import kotlin.math.sqrt

class Experience(
    /**
     * Total experience gained since the beginning
     */
    val total: Long
) {

    /**
     * Current level of experience
     */
    val level: Long

    /**
     * Experience in the current level
     */
    val current: Long

    /**
     * Experience required to the next level (not counting experience already acquired in current level)
     */
    val toNextLevel: Long

    init {
        // Calculate the level from total
        // Experience formula used is the one from Minecraft
        // See https://minecraft.fandom.com/wiki/Experience
        level = if (total < 353) {
            sqrt(total.toDouble() + 9.0).toLong() - 3
        } else if (total < 1508) {
            (81.0/10.0 + sqrt(2.0 / 5.0 * (total.toDouble() - 7839.0/40.0))).toLong()
        } else {
            (325.0/18.0 + sqrt(2.0 / 9.0 * (total.toDouble() - 54215.0 / 72.0))).toLong()
        }

        // Exp at current level to know how much we have and how much left
        val expAtLevel = if (level < 17) {
            level * level + 6 * level
        } else if (level < 32) {
            (2.5 * (level * level).toDouble() - 40.5 * level.toDouble() + 360.0).toLong()
        } else {
            (4.5 * (level * level).toDouble() - 162.5 * level.toDouble() + 2220.0).toLong()
        }
        current = total - expAtLevel
        toNextLevel = if (level < 16) {
            2 * level + 7
        } else if (level < 31) {
            5 * level - 38
        } else {
            9 * level - 158
        }
    }

}

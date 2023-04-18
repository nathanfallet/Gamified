package me.nathanfallet.gamified

import me.nathanfallet.gamified.models.Experience
import org.junit.Assert
import org.junit.Test

class ExperienceTests {

    @Test
    fun testExperienceLevel1() {
        val experience = Experience(7)
        Assert.assertEquals(1, experience.level)
        Assert.assertEquals(0, experience.current)
        Assert.assertEquals(9, experience.toNextLevel)
    }

    @Test
    fun testExperienceLevel10() {
        val experience = Experience(161)
        Assert.assertEquals(10, experience.level)
        Assert.assertEquals(1, experience.current)
        Assert.assertEquals(27, experience.toNextLevel)
    }

    @Test
    fun testExperienceLevel40() {
        val experience = Experience(2930)
        Assert.assertEquals(40, experience.level)
        Assert.assertEquals(10, experience.current)
        Assert.assertEquals(202, experience.toNextLevel)
    }

}
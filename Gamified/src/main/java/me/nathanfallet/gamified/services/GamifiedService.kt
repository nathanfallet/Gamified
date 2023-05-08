package me.nathanfallet.gamified.services

import me.nathanfallet.gamified.events.AchievementUnlockedEvent
import me.nathanfallet.gamified.events.ExperienceGainedEvent
import me.nathanfallet.gamified.models.Achievement
import me.nathanfallet.gamified.models.Experience
import me.nathanfallet.gamified.models.Graph
import me.nathanfallet.gamified.models.RegisteredAchievement
import me.nathanfallet.gamified.models.RegisteredStats
import me.nathanfallet.gamified.models.Stats
import org.greenrobot.eventbus.EventBus
import java.util.Date

class GamifiedService(
    private val globalStorage: GlobalStorageService,
    private val dailyStorage: DailyStorageService,
    registeredStats: List<RegisteredStats>,
    registeredAchievements: List<RegisteredAchievement>
) {

    // MARK: - Shared instance

    companion object {

        /**
         * Shared instance of the service, used by view models
         */
        @JvmStatic
        lateinit var shared: GamifiedService

    }

    // MARK: - Storage

    private val _registeredStats = mutableListOf<RegisteredStats>()
    private val _registeredAchievements = mutableListOf<RegisteredAchievement>()

    /**
     * Registered stats
     */
    val registeredStats: List<RegisteredStats>
        get() = _registeredStats

    /**
     * Registered achievements
     */
    val registeredAchievements: List<RegisteredAchievement>
        get() = _registeredAchievements

    // MARK: - Initializer

    init {
        registeredStats.forEach(this::registerStats)
        registeredAchievements.forEach(this::registerAchievement)
    }

    // MARK: - Stats

    /**
     * Get a stats for a day
     * @param key The stats to get
     * @param date The day to get
     * @return The value of this stats for this day
     */
    fun getStats(key: String, date: Date = Date()): Long {
        return dailyStorage.getValue(key, date)
    }

    /**
     * Set a stats for a day
     * @param key The stats to set
     * @param value The value to set
     * @param date The day to set
     */
    fun setStats(key: String, value: Long, date: Date = Date()) {
        dailyStorage.setValue(key, value, date)
    }

    /**
     * Increment a stats for a day
     * @param key The stats to increment
     * @param value The value to increment
     * @param date The day to increment
     */
    fun incrementStats(key: String, value: Long = 1, date: Date = Date()) {
        setStats(key, getStats(key, date) + value, date)
    }

    /**
     * Register a stats
     * @param key The stats to register
     */
    fun registerStats(key: RegisteredStats) {
        dailyStorage.setupValue(key.key)
        _registeredStats.add(key)
    }

    /**
     * Get a graph for stats
     * @param key: The stats to get
     * @param startDate: The starting date
     * @param endDate: The ending date
     * @return The graph corresponding to this stats between startDate and endDate
     */
    fun getGraph(key: String, startDate: Date, endDate: Date): Graph {
        return Graph(
            key,
            registeredStats.firstOrNull { it.key == key }?.name ?: "Unknown",
            dailyStorage.getValues(key, startDate, endDate).map { Stats(it.key, it.value) }
        )
    }

    /**
     * Get all graphs' stats
     * @param startDate: The starting date
     * @param endDate: The ending date
     * @return All graphs' stats between startDate and endDate
      */
    fun getGraphs(startDate: Date, endDate: Date): List<Graph> {
        return registeredStats.map { getGraph(it.key, startDate, endDate) }
    }

    // MARK: - Values

    /**
     * Get a value
     * @param key The value to get
     * @return The value
     */
    fun getValue(key: String): Long {
        return globalStorage.getValue(key)
    }

    /**
     * Set a value
     * @param key The value to set
     * @param value The value to set
     * @param incrementing: If the value should be added or replaced
     */
    fun setValue(key: String, value: Long, incrementing: Boolean = false) {
        // Save value
        val previousValue = getValue(key)
        val newValue = if (incrementing) previousValue + value else value
        globalStorage.setValue(key, newValue)

        // Check for achievements
        registeredAchievements.filter {
            key == it.key && previousValue < it.target && newValue >= it.target
        }.forEach { achievement ->
            EventBus.getDefault().post(AchievementUnlockedEvent(achievement))
            gainExperience(achievement.experience)
        }
    }

    /**
     * Increment a value
     * @param key The value to increment
     * @param value The value to add
     */
    fun incrementValue(key: String, value: Long = 1) {
        setValue(key, value, true)
    }

    // MARK: - Experience

    /**
     * Get information about experience and level
     * @return An object with all that information
     */
    fun getExperience(): Experience {
        return Experience(getValue("internal_experience"))
    }

    /**
     * Add experience to current level
     * @param exp The amount of experience to add
     * @return A pair containing experience information before and after that experience is added
     */
    fun gainExperience(exp: Long): Pair<Experience, Experience> {
        val previousExperience = getExperience()
        val newExperience = Experience(previousExperience.total + exp)
        setValue("internal_experience", newExperience.total)
        EventBus.getDefault().post(ExperienceGainedEvent(previousExperience, newExperience))

        return Pair(previousExperience, newExperience)
    }

    // MARK: - Achievements

    /**
     * Register an achievement
     * @param key: The achievement to register
     */
    fun registerAchievement(key: RegisteredAchievement) {
        _registeredAchievements.add(key)
    }

    /**
     * Get actual data for registered achievements
     * @return Data for registered achievements
     */
    fun getAchievements(): List<Achievement> {
        return registeredAchievements.map {
            Achievement(
                it.key,
                it.icon,
                it.name,
                getValue(it.key),
                it.target
            )
        }
    }

}
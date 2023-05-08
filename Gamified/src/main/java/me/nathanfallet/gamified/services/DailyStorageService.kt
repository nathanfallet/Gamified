package me.nathanfallet.gamified.services

import java.util.Date

interface DailyStorageService {

    /**
     * Setup the storage to handle a daily value for this key
     * @param key The key for this daily value
     */
    fun setupValue(key: String)

    /**
     * Get a value
     * @param key The value to get
     * @param date The day to fetch
     * @return The value for the given day
     */
    fun getValue(key: String, date: Date): Long

    /**
     * Get a values for a set of dates
     * @param key The value to get
     * @param startDate The starting date
     * @param endDate The ending date
     * @return The values for the given set of dates
     */
    fun getValues(key: String, startDate: Date, endDate: Date): Map<Date, Long>

    /**
     * Set a value for a day
     * @param key The value to set
     * @param value The value to set
     * @param date The day to set
     */
    fun setValue(key: String, value: Long, date: Date)

}
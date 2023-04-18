package me.nathanfallet.gamified.services

interface GlobalStorageService {

    /**
     * Get a value
     * @param key The value to get
     * @return The value
     */
    fun getValue(key: String): Long

    /**
     * Set a value
     * @param key The value to set
     * @param value The value to set
     */
    fun setValue(key: String, value: Long)

}
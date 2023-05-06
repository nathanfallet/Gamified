package me.nathanfallet.gamified.services

import android.database.sqlite.SQLiteOpenHelper
import me.nathanfallet.gamified.extensions.asDate
import me.nathanfallet.gamified.extensions.asString
import java.util.Date

data class SQLiteDailyStorageService(
    val helper: SQLiteOpenHelper
) : DailyStorageService {

    private val day = "day"
    private val value = "value"
    private fun table(key: String) = "gamified_$key"

    override fun setupValue(key: String) {
        helper.writableDatabase.beginTransaction()
        helper.writableDatabase.execSQL(
            "CREATE TABLE IF NOT EXISTS ${table(key)} (" +
                    "$day VARCHAR PRIMARY KEY," +
                    "$value INTEGER NOT NULL" +
                    ")"
        )
        helper.writableDatabase.setTransactionSuccessful()
        helper.writableDatabase.endTransaction()
    }

    override fun getValue(key: String, date: Date): Long {
        val cursor = helper.readableDatabase.rawQuery(
            "SELECT $day, $value FROM ${table(key)} WHERE $day == ?",
            arrayOf(date.asString)
        )
        var result: Long? = null
        if (cursor.moveToNext()) {
            result = cursor.getLong(1)
        }
        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }
        return result ?: 0
    }

    override fun getValues(key: String, startDate: Date, endDate: Date): Map<Date, Long> {
        val cursor = helper.readableDatabase.rawQuery(
            "SELECT $day, $value FROM ${table(key)} WHERE date($day) >= ? AND date($day) <= ?",
            arrayOf(startDate.asString, endDate.asString)
        )
        val result = mutableMapOf<Date, Long>()
        while (cursor.moveToNext()) {
            result[cursor.getString(0).asDate ?: Date()] = cursor.getLong(1)
        }
        if (cursor != null && !cursor.isClosed) {
            cursor.close()
        }
        return result
    }

    override fun setValue(key: String, value: Long, date: Date) {
        helper.writableDatabase.beginTransaction()
        helper.writableDatabase.execSQL(
            "INSERT OR REPLACE INTO ${table(key)} ($day, ${this.value}) VALUES (?, ?)",
            arrayOf(date.asString, value)
        )
        helper.writableDatabase.setTransactionSuccessful()
        helper.writableDatabase.endTransaction()
    }

}
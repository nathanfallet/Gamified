package me.nathanfallet.gamifiedsample

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteOpenHelper
import android.widget.RemoteViewsService
import androidx.appcompat.app.AppCompatActivity
import me.nathanfallet.gamified.models.RegisteredAchievement
import me.nathanfallet.gamified.models.RegisteredStats
import me.nathanfallet.gamified.services.AbstractStatsWidgetRemoteViewsFactory
import me.nathanfallet.gamified.services.GamifiedService
import me.nathanfallet.gamified.services.SQLiteDailyStorageService
import me.nathanfallet.gamified.services.SharedPreferencesGlobalStorageService

class StatsWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StatsWidgetRemoteViewsFactory(applicationContext)
    }

}

class StatsWidgetRemoteViewsFactory(context: Context) :
    AbstractStatsWidgetRemoteViewsFactory(context) {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sqlHelper: SQLiteOpenHelper

    override fun initializeGamifiedServiceIfNeeded() {
        if (!this::sharedPreferences.isInitialized) {
            sharedPreferences =
                context.getSharedPreferences("gamified", AppCompatActivity.MODE_PRIVATE)
        }
        if (!this::sqlHelper.isInitialized) {
            sqlHelper = TestDatabase(context)
        }
        GamifiedService.shared = GamifiedService(
            SharedPreferencesGlobalStorageService(sharedPreferences),
            SQLiteDailyStorageService(sqlHelper),
            listOf(
                RegisteredStats("test", "Test")
            ),
            listOf(
                RegisteredAchievement("test", "test", R.drawable.ic_launcher_foreground, 10, 10)
            )
        )
    }

}
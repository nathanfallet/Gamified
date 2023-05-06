package me.nathanfallet.gamifiedsample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteOpenHelper
import android.widget.RemoteViews
import androidx.appcompat.app.AppCompatActivity
import me.nathanfallet.gamified.models.RegisteredAchievement
import me.nathanfallet.gamified.models.RegisteredStats
import me.nathanfallet.gamified.services.GamifiedService
import me.nathanfallet.gamified.services.SQLiteDailyStorageService
import me.nathanfallet.gamified.services.SharedPreferencesGlobalStorageService
import me.nathanfallet.gamified.services.StatsWidgetService

class StatsWidget : AppWidgetProvider() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sqlHelper: SQLiteOpenHelper

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // There may be multiple widgets active, so update all of them
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onEnabled(context: Context) {
        // Init services
        if (!this::sharedPreferences.isInitialized) {
            sharedPreferences =
                context.getSharedPreferences("gamified", AppCompatActivity.MODE_PRIVATE)
        }
        if (!this::sqlHelper.isInitialized) {
            sqlHelper = TestDatabase(context)
        }

        // Enter relevant functionality for when the first widget is created
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

    override fun onDisabled(context: Context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

internal fun updateAppWidget(
    context: Context,
    appWidgetManager: AppWidgetManager,
    appWidgetId: Int
) {
    // Construct the RemoteViews object
    val views = RemoteViews(context.packageName, R.layout.stats_widget)
    val intent = Intent(context, StatsWidgetService::class.java)
    intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);
    views.setRemoteAdapter(R.id.gridView, intent)
    views.setEmptyView(R.id.gridView, R.id.empty_view)

    // Instruct the widget manager to update the widget
    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetId, R.id.gridView)
    appWidgetManager.updateAppWidget(appWidgetId, views)
}
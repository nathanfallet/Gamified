package me.nathanfallet.gamifiedsample

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import me.nathanfallet.gamified.models.RegisteredAchievement
import me.nathanfallet.gamified.models.RegisteredStats
import me.nathanfallet.gamified.services.DailyStorageService
import me.nathanfallet.gamified.services.GamifiedService
import me.nathanfallet.gamified.services.GlobalStorageService
import me.nathanfallet.gamified.services.StatsWidgetService
import java.util.Date

class StatsWidget : AppWidgetProvider(), GlobalStorageService, DailyStorageService {

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
        // Enter relevant functionality for when the first widget is created
        GamifiedService.shared = GamifiedService(
            this,
            this,
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

    override fun setupValue(key: String) {

    }

    override fun getValue(key: String, date: Date): Long {
        return 0
    }

    override fun getValues(key: String, startDate: Date, endDate: Date): Map<Date, Long> {
        return mapOf()
    }

    override fun setValue(key: String, value: Long, date: Date) {

    }

    override fun getValue(key: String): Long {
        return 0
    }

    override fun setValue(key: String, value: Long) {

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
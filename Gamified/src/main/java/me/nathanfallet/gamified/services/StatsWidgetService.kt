package me.nathanfallet.gamified.services

import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.content.ContextCompat
import me.nathanfallet.gamified.R
import me.nathanfallet.gamified.extensions.asString
import me.nathanfallet.gamified.extensions.getDays
import me.nathanfallet.gamified.extensions.nextStartOfWeek
import me.nathanfallet.gamified.extensions.previous17Weeks
import me.nathanfallet.gamified.extensions.previous7Weeks
import me.nathanfallet.gamified.models.Stats
import java.util.Date

class StatsWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory {
        return StatsWidgetRemoteViewsFactory(applicationContext)
    }

}

class StatsWidgetRemoteViewsFactory(val context: Context) : RemoteViewsService.RemoteViewsFactory {

    private val rows = 7
    private val columns: Int
        get() {
            return ((stats.size - 1) / rows) + 1
        }

    var stats: List<Stats> = listOf()

    override fun onCreate() {
        // Nothing
    }

    override fun onDataSetChanged() {
        val start = previous7Weeks
        val end = Date()

        val raw = GamifiedService.shared.getGraphs(start, end).flatMap { it.stats }
        stats = previous17Weeks.nextStartOfWeek.getDays(Date()).map { day ->
            raw.filter { it.day.asString == day.asString }.fold(Stats(day, 0)) { acc, stats ->
                Stats(acc.day, acc.value + stats.value)
            }
        }
    }

    override fun onDestroy() {
        // Nothing
    }

    override fun getCount(): Int {
        return rows * columns
    }

    override fun getViewAt(position: Int): RemoteViews {
        val views = RemoteViews(context.packageName, R.layout.stats_widget_item)

        val row = position / columns
        val column = position % columns
        val color = ContextCompat.getColor(context, color(row, column))
        views.setInt(R.id.background, "setColorFilter", color)
        views.setInt(R.id.background, "setImageAlpha", color.shr(24))

        return views
    }

    override fun getLoadingView(): RemoteViews {
        return RemoteViews(context.packageName, R.layout.stats_widget_item)
    }

    override fun getViewTypeCount(): Int {
        return 1
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun hasStableIds(): Boolean {
        return true
    }

    fun color(row: Int, column: Int): Int {
        val index = row + rows * column
        if (index >= stats.size) return android.R.color.transparent

        val max = stats.maxOf { it.value }
        if (max == 0L) return color(0f)

        return color(stats[index].value.toFloat() / max.toFloat())
    }

    fun color(level: Float): Int {
        return when {
            level == 0f -> R.color.accentDisabled
            level <= 0.25 -> R.color.accentVeryLight
            level <= 0.5 -> R.color.accentLight
            level <= 0.75 -> R.color.accent
            else -> R.color.accentDark
        }
    }

}
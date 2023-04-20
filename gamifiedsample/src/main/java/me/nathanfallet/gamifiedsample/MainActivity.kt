package me.nathanfallet.gamifiedsample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import me.nathanfallet.gamified.activities.StatsActivity
import me.nathanfallet.gamified.models.Counter
import me.nathanfallet.gamified.models.RegisteredAchievement
import me.nathanfallet.gamified.models.RegisteredStats
import me.nathanfallet.gamified.services.DailyStorageService
import me.nathanfallet.gamified.services.GamifiedService
import me.nathanfallet.gamified.services.GlobalStorageService
import java.util.Date

class MainActivity : AppCompatActivity(), GlobalStorageService, DailyStorageService {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.button_stats).setOnClickListener {
            val intent = Intent(this, StatsActivity::class.java)
            intent.putExtra(StatsActivity.Extras.isGridEnabled, true)
            intent.putExtra(
                StatsActivity.Extras.counters, arrayListOf(
                    Counter(
                        R.drawable.ic_launcher_foreground,
                        "Test counter",
                        123
                    )
                )
            )
            startActivity(intent)
        }
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
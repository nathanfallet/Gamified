package me.nathanfallet.gamifiedsample

import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteOpenHelper
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import me.nathanfallet.gamified.activities.StatsActivity
import me.nathanfallet.gamified.models.Counter
import me.nathanfallet.gamified.models.RegisteredAchievement
import me.nathanfallet.gamified.models.RegisteredStats
import me.nathanfallet.gamified.services.GamifiedService
import me.nathanfallet.gamified.services.SQLiteDailyStorageService
import me.nathanfallet.gamified.services.SharedPreferencesGlobalStorageService

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var sqlHelper: SQLiteOpenHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sharedPreferences = getSharedPreferences("gamified", MODE_PRIVATE)
        sqlHelper = TestDatabase(this)

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

        findViewById<Button>(R.id.button_increment).setOnClickListener {
            GamifiedService.shared.incrementStats("test", 1)
            GamifiedService.shared.incrementValue("test", 1)
        }
    }

}
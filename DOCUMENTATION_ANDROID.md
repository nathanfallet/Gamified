# Full documentation

## Configure the package

Create the shared instance of `GamifiedService` and configure it with your stats and achievements.

```kotlin
GamifiedService.shared = GamifiedService(
    myGlobalStorageService,
    myDailyStorageService(sqlHelper),
    listOf(
        RegisteredStats("daily_thing", "Daily thing")
    ),
    listOf(
        RegisteredAchievement("victories", "10 victories", R.drawable.achievement_10_victories, 10, 5)
    )
)
```

You can use a `SharedPreferencesGlobalStorageService` instance for `globalStorage` and
a `SQLiteDailyStorageService` for `dailyStorage` if you want to store data locally, or create a
custom implementation implementing interfaces.

## Update stats and values

Update values and stats using the `incrementValue` and `incrementStats` methods. You can also
specify by how much you want to increment the value as a second argument.

```kotlin
GamifiedService.shared.incrementStats("daily_thing")
GamifiedService.shared.incrementValue("victories")
```

## Views

### Game banner

In all activities where you want to show the game banner, add the following code:

```kotlin
class MyActivity: AppCompatActivity() {

    private val bannerView = BannerView(this)
    
    override fun onResume() {
        super.onResume()
        bannerView.register()
    }

    override fun onPause() {
        super.onPause()
        bannerView.unregister()
    }

}
```

### Stats

```kotlin
val intent = Intent(this, StatsActivity::class.java)
intent.putExtra(StatsActivity.Extras.isGridEnabled, true)
intent.putExtra(
    StatsActivity.Extras.counters, arrayListOf(
        Counter(
            R.drawable.counter_icon,
            "Counter name",
            123
        )
    )
)
startActivity(intent)
```

### Game profile

```kotlin
startActivity(Intent(this, GameProfileActivity::class.java))
```

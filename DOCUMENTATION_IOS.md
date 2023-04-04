# Full documentation

## Import the package

```swift
import Gamified
```

## Configure the package

Create a shared instance of `GamifiedService` and configure it with your stats and achievements.

```swift
extension GamifiedService {

    static let shared = GamifiedService(
        globalStorage: myGlobalStorageService,
        dailyStorage: myDailyStorageService,
        registeredStats: [
            RegisteredStats(key: "daily_thing", name: "Daily thing")
        ],
        registeredAchievements: [
            RegisteredAchievement(key: "victories", name: "10 victories", icon: "10Victories", target: 10, experience: 5)
        ]
    )

}
```

You can use a `UserDefaults` instance for `globalStorage` and a SQLite.swift's `Connection` for `dailyStorage` if you want to store data locally, or create a custom implementation implementing interfaces.

## Update stats and values

Update values and stats using the `incrementValue` and `incrementStats` methods. You can also specify by how much you want to increment the value as a second argument.

```swift
GamifiedService.shared.incrementStats("daily_thing")
GamifiedService.shared.incrementValue("victories")
```

## Views

### Game banner

```swift
NavigationView {
    // ...
}
.addingGameBanner()
```

### Stats

```swift
StatsView(
    service: .shared,
    counters: [
        Counter(
            icon: "CounterIcon",
            text: "Counter name",
            count: 123
        ),
        // ...
    ],
    isGridEnabled: true
)
```

### Game profile

```swift
GameProfileView(
    service: .shared
)
```

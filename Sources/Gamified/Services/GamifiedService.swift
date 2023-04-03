//
//  GamifiedService.swift
//  
//
//  Created by Nathan Fallet on 28/01/2023.
//

import Foundation

public class GamifiedService {
    
    // MARK: - Storage
    
    private let globalStorage: GlobalStorageService
    private let dailyStorage: DailyStorageService
    
    /// Registered stats
    public private(set) var registeredStats = [RegisteredStats]()
    
    /// Registered achievements
    public private(set) var registeredAchievements = [RegisteredAchievement]()
    
    // MARK: - Initializer
    
    public init(
        globalStorage: GlobalStorageService,
        dailyStorage: DailyStorageService,
        registeredStats: [RegisteredStats],
        registeredAchievements: [RegisteredAchievement]
    ) {
        self.globalStorage = globalStorage
        self.dailyStorage = dailyStorage
        
        registeredStats.forEach(registerStats)
        registeredAchievements.forEach(registerAchievement)
    }
    
    // MARK: - Stats
    
    /// Get a stats
    /// - Parameters:
    ///   - key: The stats to get
    ///   - date: The day to fetch
    /// - Returns: The value of the stats
    public func getStats(_ key: String, for date: Date = Date()) -> Int {
        return dailyStorage.getValue(key, for: date)
    }
    
    /// Set a stats for a day
    /// - Parameters:
    ///   - key: The stats to set
    ///   - value: The value to set
    ///   - date: The day to set
    public func setStats(_ key: String, value: Int, for date: Date = Date()) {
        dailyStorage.setValue(key, value: value, for: date)
    }
    
    /// Increment a stats for a day
    /// - Parameters:
    ///   - key: The stats to increment
    ///   - value: The value to add
    ///   - date: The day to set
    public func incrementStats(_ key: String, by value: Int = 1, for date: Date = Date()) {
        setStats(key, value: getStats(key, for: date) + value, for: date)
    }
    
    /// Register a stats
    /// - Parameter key: The stats to register
    public func registerStats(_ key: RegisteredStats) {
        dailyStorage.setupValue(key.key)
        registeredStats.append(key)
    }
    
    /// Get a graph for stats
    /// - Parameters:
    ///   - key: The stats to get
    ///   - startDate: The starting date
    ///   - endDate: The ending date
    /// - Returns: The graph corresponding to this stats between startDate and endDate
    public func getGraph(_ key: String, startDate: Date, endDate: Date) -> Graph {
        return Graph(
            key: key,
            title: registeredStats.first(where: { $0.key == key })?.name ?? "Unknown",
            stats: dailyStorage
                .getValues(key, startDate: startDate, endDate: endDate)
                .map { date, value in
                    Stats(day: date, value: value)
                }
        )
    }
    
    /// Get graphs' stats
    /// - Parameters:
    ///   - startDate: The starting date
    ///   - endDate: The ending date
    /// - Returns: All graphs' stats between startDate and endDate
    public func getGraphs(startDate: Date, endDate: Date) -> [Graph] {
        return registeredStats.map { key in
            getGraph(key.key, startDate: startDate, endDate: endDate)
        }
    }
    
    // MARK: - Values
    
    /// Get a value
    /// - Parameter key: The value to get
    /// - Returns: The value
    public func getValue(_ key: String) -> Int {
        return globalStorage.getValue(key)
    }
    
    /// Set a value
    /// - Parameters:
    ///   - key: The value to set
    ///   - value: The value to set
    public func setValue(_ key: String, value: Int) {
        globalStorage.setValue(key, value: value)
    }
    
    /// Increment a value
    /// - Parameters:
    ///   - key: The value to increment
    ///   - value: The value to add
    public func incrementValue(_ key: String, by value: Int = 1) {
        setValue(key, value: getValue(key) + value)
    }
    
    // MARK: - Experience
    
    /// Get information about experience and level
    /// - Returns: An object with all that information
    public func getExperience() -> Experience {
        return Experience(total: getValue("internal_experience"))
    }
    
    @discardableResult
    /// Add experience to current level
    /// - Parameter exp: The amount of experience to add
    /// - Returns: A tuple containing experience information before and after that experience is added
    public func gainExperience(_ exp: Int) -> (Experience, Experience) {
        let previousExperience = getExperience()
        let newExperience = Experience(total: previousExperience.total + exp)
        setValue("internal_experience", value: newExperience.total)
        NotificationCenter.default.post(
            name: .experienceGained,
            object: nil,
            userInfo: [
                "previousExperience": previousExperience,
                "newExperience": newExperience
            ]
        )
        
        return (previousExperience, newExperience)
    }
    
    // MARK: - Achievements
    
    /// Register an achievement
    /// - Parameter key: The achievement to register
    public func registerAchievement(_ key: RegisteredAchievement) {
        registeredAchievements.append(key)
    }
    
    /// Get actual data for registered achievements
    /// - Returns: Data for registered achievements
    public func getAchievements() -> [Achievement] {
        return registeredAchievements.map { key in
            Achievement(
                key: key.key,
                icon: key.icon,
                text: key.name,
                value: getValue(key.key),
                target: key.target
            )
        }
    }
    
}

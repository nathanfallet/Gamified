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
    
    /// Keys of registered stats
    public private(set) var registeredStats = [String]()
    
    // MARK: - Initializer
    
    public init(
        globalStorage: GlobalStorageService,
        dailyStorage: DailyStorageService,
        registeredStats: [String]
    ) {
        self.globalStorage = globalStorage
        self.dailyStorage = dailyStorage
        
        registeredStats.forEach(registerStats)
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
    public func registerStats(_ key: String) {
        dailyStorage.setupValue(key)
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
            title: key,
            stats: dailyStorage
                .getValues(key, startDate: startDate, endDate: endDate)
                .map { date, value in
                    Stats(day: date, value: value)
                }
        )
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
    
    // MARK: - Achievements
    
    
    
}

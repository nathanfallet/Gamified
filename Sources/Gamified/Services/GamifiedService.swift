//
//  GamifiedService.swift
//  
//
//  Created by Nathan Fallet on 28/01/2023.
//

import Foundation

public struct GamifiedService {
    
    // MARK: - Storage
    
    
    // MARK: - Initializer
    
    public init(
        
    ) {
        
    }
    
    // MARK: - Stats
    
    /// Get a stats
    /// - Parameters:
    ///   - key: The stats to get
    ///   - date: The day to fetch
    /// - Returns: The value of the stats
    public func getStats(_ key: String, for date: Date = Date()) -> Int {
        // TODO: Implement this (using storage)
        return 0
    }
    
    /// Set a stats for today
    /// - Parameters:
    ///   - key: The stats to set
    ///   - value: The value to set
    ///   - date: The day to set
    public func setStats(_ key: String, value: Int, for date: Date = Date()) {
        // TODO: Implement this (using storage)
    }
    
    /// Increment a stats for today
    /// - Parameters:
    ///   - key: The stats to increment
    ///   - value: The value to add
    ///   - date: The day to set
    public func incrementStats(_ key: String, by value: Int = 1, for date: Date = Date()) {
        setStats(key, value: getStats(key, for: date) + value, for: date)
    }
    
    /// Get a graph for stats
    /// - Parameters:
    ///   - key: The stats to get
    ///   - startDate: The starting date
    ///   - endDate: The ending date
    /// - Returns: The graph corresponding to this stats between startDate and endDate
    public func getGraph(_ key: String, startDate: Date, endDate: Date) -> Graph {
        // TODO: Implement this (using storage)
        return Graph(title: key, stats: [])
    }
    
    // MARK: - Values
    
    /// Get a value
    /// - Parameter key: The value to get
    /// - Returns: The value
    public func getValue(_ key: String) -> Int {
        // TODO: Implement this (using storage)
        return 0
    }
    
    /// Set a value
    /// - Parameters:
    ///   - key: The value to set
    ///   - value: The value to set
    public func setValue(_ key: String, value: Int) {
        // TODO: Implement this (using storage)
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

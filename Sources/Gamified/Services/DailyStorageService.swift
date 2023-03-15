//
//  DailyStorageService.swift
//  
//
//  Created by Nathan Fallet on 15/03/2023.
//

import Foundation

public protocol DailyStorageService {
    
    /// Get a value
    /// - Parameters:
    ///   - key: The value to get
    ///   - date: The day to fetch
    /// - Returns: The value for the given day
    func getValue(_ key: String, for date: Date) -> Int
    
    /// Get a values for a set of dates
    /// - Parameters:
    ///   - key: The value to get
    ///   - startDate: The starting date
    ///   - endDate: The ending date
    /// - Returns: The values for the given set of dates
    func getValues(_ key: String, startDate: Date, endDate: Date) -> [Date: Int]
    
    /// Set a value for a day
    /// - Parameters:
    ///   - key: The value to set
    ///   - value: The value to set
    ///   - date: The day to set
    func setValue(_ key: String, value: Int, for date: Date)
    
}

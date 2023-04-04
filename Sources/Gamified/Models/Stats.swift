//
//  Stats.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public struct Stats {
    
    /// The day of the entry
    public var day: Date
    
    /// The value of the entry
    public var value: Int
    
    /// Create a new entry
    /// - Parameters:
    ///   - day: The day of the entry
    ///   - value: The value of the entry
    public init(
        day: Date,
        value: Int
    ) {
        self.day = day
        self.value = value
    }
    
}

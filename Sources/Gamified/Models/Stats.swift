//
//  Stats.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public struct Stats {
    
    /// The day of the entry
    public var day: String
    
    /// The value of the entry
    public var value: Int
    
    /// Create a new entry
    /// - Parameters:
    ///   - day: The day of the entry
    ///   - value: The value of the entry
    public init(
        day: String,
        value: Int
    ) {
        self.day = day
        self.value = value
    }
    
}

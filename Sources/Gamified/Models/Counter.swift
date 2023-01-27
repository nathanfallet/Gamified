//
//  Counter.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public struct Counter {
    
    /// Icon shown in the counter
    public var icon: String
    
    /// Text shown in the counter
    public var text: String
    
    /// Value of the counter
    public var count: Int
    
    /// Create a counter in the view
    /// - Parameters:
    ///   - icon: Icon shown in the counter
    ///   - text: Text shown in the counter
    ///   - count: Value of the counter
    public init(
        icon: String,
        text: String,
        count: Int
    ) {
        self.icon = icon
        self.text = text
        self.count = count
    }
    
}

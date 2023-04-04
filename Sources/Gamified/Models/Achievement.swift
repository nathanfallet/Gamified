//
//  Achievement.swift
//  
//
//  Created by Nathan Fallet on 03/04/2023.
//

import Foundation

public struct Achievement: Identifiable {
    
    public var id: String {
        return "\(key)_\(target)"
    }
    
    /// Key of the achievement
    public var key: String
    
    /// Icon shown in the achievement
    public var icon: String
    
    /// Text shown in the achievement
    public var text: String
    
    /// The value of the achievement
    public var value: Int
    
    /// The target value of the achievement
    public var target: Int
    
    /// Whether the achievement is unlocked
    public var unlocked: Bool {
        return value >= target
    }
    
    /// Create a new entry
    /// - Parameters:
    ///   - key: Key of the achievement
    ///   - icon: Icon shown in the achievement
    ///   - text: Text shown in the achievement
    ///   - value: The value of the achievement
    public init(
        key: String,
        icon: String,
        text: String,
        value: Int,
        target: Int
    ) {
        self.key = key
        self.icon = icon
        self.text = text
        self.value = min(value, target)
        self.target = target
    }
    
}

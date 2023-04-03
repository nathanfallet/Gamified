//
//  RegisteredAchievement.swift
//  
//
//  Created by Nathan Fallet on 03/04/2023.
//

import Foundation

public struct RegisteredAchievement {
    
    /// Key of the achievement
    public var key: String
    
    /// Name of the achievement
    public var name: String
    
    /// Icon shown in the achievement
    public var icon: String
    
    /// Target value to unlock this achievement
    public var target: Int
    
    /// Create a registered achievement
    /// - Parameters:
    ///   - key: Key of the achievement
    ///   - name: Name of the achievement
    ///   - icon: Icon shown in the achievement
    ///   - target: Target value to unlock this achievement
    public init(
        key: String,
        name: String,
        icon: String,
        target: Int
    ) {
        self.key = key
        self.name = name
        self.icon = icon
        self.target = target
    }
    
}

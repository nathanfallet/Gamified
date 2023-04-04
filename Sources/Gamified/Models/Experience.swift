//
//  Experience.swift
//  
//
//  Created by Nathan Fallet on 25/03/2023.
//

import Foundation

public struct Experience {
    
    /// Total experience gained since the beginning
    public let total: Int
    
    /// Current level of experience
    public let level: Int
    
    /// Experience in the current level
    public let current: Int
    
    /// Experience required to the next level (not counting experience already acquired in current level)
    public let toNextLevel: Int
    
    /// Initialize an experience object, giving information about level
    /// - Parameter total: Total experience gained since the beginning
    public init(total: Int) {
        self.total = total
        
        // Calculate the level from total
        // Experience formula used is the one from Minecraft
        // See https://minecraft.fandom.com/wiki/Experience
        if total < 353 {
            self.level = Int(sqrt(Double(total + 9))) - 3
        } else if total < 1508 {
            self.level = Int(81/10 + sqrt(2 / 5 * (Double(total) - 7839/40)))
        } else {
            self.level = Int(325/18 + sqrt(2 / 9 * (Double(total) - 54215 / 72)))
        }
        
        // Exp at current level to know how much we have and how much left
        let expAtLevel: Int
        if level < 17 {
            expAtLevel = level * level + 6 * level
        } else if level < 32 {
            expAtLevel = Int(2.5 * Double(level * level) - 40.5 * Double(level) + 360)
        } else {
            expAtLevel = Int(4.5 * Double(level * level) - 162.5 * Double(level) + 2220)
        }
        self.current = total - expAtLevel
        if level < 16 {
            self.toNextLevel = 2 * level + 7
        } else if level < 31 {
            self.toNextLevel = 5 * level - 38
        } else {
            self.toNextLevel = 9 * level - 158
        }
    }
    
}

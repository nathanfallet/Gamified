//
//  GlobalStorageService.swift
//  
//
//  Created by Nathan Fallet on 15/03/2023.
//

import Foundation

public protocol GlobalStorageService {
    
    /// Get a value
    /// - Parameter key: The value to get
    /// - Returns: The value
    func getValue(_ key: String) -> Int
    
    /// Set a value
    /// - Parameters:
    ///   - key: The value to set
    ///   - value: The value to set
    func setValue(_ key: String, value: Int)
    
}

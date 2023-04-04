//
//  RegisteredStats.swift
//  
//
//  Created by Nathan Fallet on 03/04/2023.
//

import Foundation

public struct RegisteredStats {
    
    /// Key of the stats
    public var key: String
    
    /// Name of the stats
    public var name: String
    
    /// Create a registered stats
    /// - Parameters:
    ///   - key: Key of the graph
    ///   - name: Name of the graph
    public init(
        key: String,
        name: String
    ) {
        self.key = key
        self.name = name
    }
    
}

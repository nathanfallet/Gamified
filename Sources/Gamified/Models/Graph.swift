//
//  Graph.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public struct Graph {
    
    /// Key of the graph
    public var key: String
    
    /// Title of the graph
    public var title: String
    
    /// Values of the graph
    public var stats: [Stats]
    
    /// Create a graph
    /// - Parameters:
    ///   - key: Key of the graph
    ///   - title: Title of the graph
    ///   - stats: Values of the graph
    public init(
        key: String,
        title: String,
        stats: [Stats]
    ) {
        self.key = key
        self.title = title
        self.stats = stats
    }
    
}

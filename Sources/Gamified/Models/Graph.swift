//
//  Graph.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public struct Graph {
    
    /// Title of the graph
    public var title: String
    
    /// Values of the graph
    public var stats: [Stats]
    
    /// Create a graph
    /// - Parameters:
    ///   - title: Title of the graph
    ///   - stats: Values of the graph
    public init(
        title: String,
        stats: [Stats]
    ) {
        self.title = title
        self.stats = stats
    }
    
}

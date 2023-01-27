//
//  File.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public struct GamifiedConfig {
    
    /// Counters shown in the view
    public var counters: [Counter]
    
    /// If the grid should be shown in the view
    public var isGridEnabled: Bool
    
    /// Graphs shown in the view
    public var graphs: [Graph]
    
    /// Custom action called when the view appears
    public var onAppear: () -> Void
    
    /// Create a configuration for gamified
    /// - Parameters:
    ///   - counters: Counters shown in the view
    ///   - isGridEnabled: If the grid should be shown in the view
    ///   - graphs: Graphs shown in the view
    ///   - onAppear: Custom action called when the view appears (optional)
    public init(
        counters: [Counter],
        isGridEnabled: Bool,
        graphs: [Graph],
        onAppear: @escaping () -> Void = {}
    ) {
        self.counters = counters
        self.isGridEnabled = isGridEnabled
        self.graphs = graphs
        self.onAppear = onAppear
    }
    
}

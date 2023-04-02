//
//  StatsViewModel.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

class StatsViewModel: ObservableObject {
    
    // Configuration
    
    let service: GamifiedService
    let counters: [Counter]
    let graphs: [Graph]
    let isGridEnabled: Bool
    let onAppear: () -> Void
    
    // Initializer
    
    init(
        service: GamifiedService,
        counters: [Counter],
        isGridEnabled: Bool,
        onAppear: @escaping () -> Void = {}
    ) {
        self.service = service
        self.counters = counters
        self.isGridEnabled = isGridEnabled
        self.onAppear = onAppear
        self.graphs = service.getGraphs(startDate: .previous17Weeks, endDate: Date())
    }
    
}

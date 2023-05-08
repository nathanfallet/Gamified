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
    let isGridEnabled: Bool
    
    @Published var graphs: [Graph]
    
    // Initializer
    
    init(
        service: GamifiedService,
        counters: [Counter],
        isGridEnabled: Bool
    ) {
        self.service = service
        self.counters = counters
        self.isGridEnabled = isGridEnabled
        self.graphs = []
        DispatchQueue.global().async {
            let graphsData = service.getGraphs(startDate: .previous17Weeks, endDate: Date())
            DispatchQueue.main.async {
                self.graphs = graphsData
            }
        }
    }
    
}

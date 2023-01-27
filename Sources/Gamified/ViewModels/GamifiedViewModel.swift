//
//  StatsViewModel.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

class GamifiedViewModel: ObservableObject {
    
    // Configuration
    
    let configuration: GamifiedConfig
    
    // Initializer
    
    init(configuration: GamifiedConfig) {
        self.configuration = configuration
    }
    
    // Methods
    
    func onAppear() {
        configuration.onAppear()
    }
    
}

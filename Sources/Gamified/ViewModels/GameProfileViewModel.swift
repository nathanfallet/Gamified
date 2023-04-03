//
//  GameProfileViewModel.swift
//  
//
//  Created by Nathan Fallet on 03/04/2023.
//

import Foundation

class GameProfileViewModel: ObservableObject {
    
    // Configuration
    
    let service: GamifiedService
    let achievements: [Achievement]
    let onAppear: () -> Void
    
    // Initializer
    
    init(
        service: GamifiedService,
        onAppear: @escaping () -> Void = {}
    ) {
        self.service = service
        self.achievements = []
        self.onAppear = onAppear
    }
    
}


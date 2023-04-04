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
    let experience: Experience
    let achievements: [Achievement]
    let onAppear: () -> Void
    
    // Initializer
    
    init(
        service: GamifiedService,
        onAppear: @escaping () -> Void = {}
    ) {
        self.service = service
        self.experience = service.getExperience()
        self.achievements = service.getAchievements()
        self.onAppear = onAppear
    }
    
}


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
    let username: String?
    let url: URL?
    let experience: Experience
    let achievements: [Achievement]
    
    // Initializer
    
    init(
        service: GamifiedService
    ) {
        self.service = service
        self.username = nil
        self.url = nil
        self.experience = service.getExperience()
        self.achievements = service.getAchievements()
    }
    
}


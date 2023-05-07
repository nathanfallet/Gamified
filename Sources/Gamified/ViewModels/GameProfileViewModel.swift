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
    
    @Published var experience: Experience
    @Published var achievements: [Achievement]
    
    // Initializer
    
    init(
        service: GamifiedService
    ) {
        self.service = service
        self.username = nil
        self.url = nil
        self.experience = Experience(total: 0)
        self.achievements = []
        DispatchQueue.global().async {
            let experienceData = service.getExperience()
            DispatchQueue.main.async {
                self.experience = experienceData
            }
            let achievementsData = service.getAchievements()
            DispatchQueue.main.async {
                self.achievements = achievementsData
            }
        }
    }
    
}


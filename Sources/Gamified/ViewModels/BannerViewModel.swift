//
//  BannerViewModel.swift
//  
//
//  Created by Nathan Fallet on 25/03/2023.
//

import Foundation

class BannerViewModel: ObservableObject {
    
    // General data
    @Published var type: String?
    
    // Gained experience data
    @Published var progressBarValue: Double?
    @Published var progressBarTotal: Double?
    @Published var progressBarLevel: Int?
    
    // Achivement unlocked data
    @Published var achievementName: String?
    @Published var achievementIcon: String?
    
    init() {
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(experienceGained),
            name: .experienceGained,
            object: nil
        )
        NotificationCenter.default.addObserver(
            self,
            selector: #selector(achievementUnlocked),
            name: .achievementUnlocked,
            object: nil
        )
    }
    
    deinit {
        NotificationCenter.default.removeObserver(self)
    }
    
    func checkForNotificationInProgress(_ action: @escaping (NSNotification) -> Void, notification: NSNotification) -> Bool {
        if type != nil {
            DispatchQueue.main.asyncAfter(deadline: .now() + 4) {
                action(notification)
            }
            return true
        }
        return false
    }
    
    @objc func experienceGained(_ notification: NSNotification) {
        // Check that no notification is already shown. Else, delay
        if checkForNotificationInProgress(experienceGained, notification: notification) {
            return
        }
        
        // Set base data
        type = "experienceGained"
        let previousExperience = notification.userInfo?["previousExperience"] as? Experience
        let newExperience = notification.userInfo?["newExperience"] as? Experience
        progressBarValue = Double(previousExperience?.current ?? 0)
        progressBarTotal = Double(previousExperience?.toNextLevel ?? 0)
        progressBarLevel = previousExperience?.level
        
        // Schedule a timer to move the progress bar
        Timer.scheduledTimer(withTimeInterval: 0.05, repeats: true) { timer in
            // Add a small amount of progress
            let diff = Double(newExperience?.total ?? 0) - Double(previousExperience?.total ?? 0)
            self.progressBarValue = (self.progressBarValue ?? 0) + diff * 0.05
            
            // Check if we hit the max (to go to next level and play an effect)
            if self.progressBarLevel != newExperience?.level && Int(self.progressBarValue ?? 0) >= previousExperience?.toNextLevel ?? 0 {
                self.progressBarValue = 0
                self.progressBarTotal = Double(newExperience?.toNextLevel ?? 0)
                self.progressBarLevel = newExperience?.level
            }
            
            // Check if we are done
            if self.progressBarLevel == newExperience?.level && Int(self.progressBarValue ?? 0) >= newExperience?.current ?? 0 {
                timer.invalidate()
            }
        }
    }
    
    @objc func achievementUnlocked(_ notification: NSNotification) {
        // Check that no notification is already shown. Else, delay
        if checkForNotificationInProgress(achievementUnlocked, notification: notification) {
            return
        }
        
        // Set base data
        type = "achievementUnlocked"
        let achievement = notification.userInfo?["achievement"] as? RegisteredAchievement
        achievementName = achievement?.name
        achievementIcon = achievement?.icon
    }
    
}

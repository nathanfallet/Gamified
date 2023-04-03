//
//  AchievementView.swift
//  
//
//  Created by Nathan Fallet on 03/04/2023.
//

import SwiftUI

public struct AchievementView: View {
    
    let achievement: Achievement
    
    public var body: some View {
        HStack {
            Spacer(minLength: 0)
            VStack(spacing: 12) {
                Image(achievement.icon)
                    .resizable()
                    .frame(width: 44, height: 44)
                Text(achievement.text)
                // TODO: Add a progress bar if needed, and change image color if not unlocked
            }
            Spacer(minLength: 0)
        }
    }
    
}

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
                    .saturation(achievement.value < achievement.target ? 0.0 : 1.0)
                    .frame(width: 50, height: 50)
                Text(achievement.text)
                if achievement.target > 1 {
                    ProgressView(
                        value: Double(achievement.value),
                        total: Double(achievement.target)
                    )
                }
            }
            Spacer(minLength: 0)
        }
    }
    
}

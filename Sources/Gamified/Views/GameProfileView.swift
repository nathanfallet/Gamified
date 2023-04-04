//
//  GameProfileView.swift
//  
//
//  Created by Nathan Fallet on 03/04/2023.
//

import SwiftUI
import Kingfisher

public struct GameProfileView: View {
    
    @StateObject var viewModel: GameProfileViewModel
    
    /// Create a game profile view
    /// - Parameters:
    ///   - service: Gamified service used as source for data
    ///   - onAppear: Custom action called when the view appears (optional)
    public init(
        service: GamifiedService
    ) {
        self._viewModel = StateObject(wrappedValue: GameProfileViewModel(
            service: service
        ))
    }
    
    public var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                // Header
                HStack {
                    if let url = viewModel.url {
                        KFImage(url)
                            .placeholder({ Color.gray })
                            .resizable()
                            .frame(width: 44, height: 44)
                            .cornerRadius(22)
                    }
                    VStack {
                        HStack {
                            if let username = viewModel.username {
                                Text(username)
                                Spacer()
                            }
                            Text(LocalizedStringKey("banner_experience_gained_level \(viewModel.experience.level)"), bundle: .module)
                        }
                        ProgressView(
                            value: Double(viewModel.experience.current),
                            total: Double(viewModel.experience.toNextLevel)
                        )
                    }
                }
                .cardView()
                
                // Achievements
                Text(LocalizedStringKey("profile_achievements"), bundle: .module)
                    .font(.title)
                    .padding(.top)
                LazyVGrid(columns: [GridItem(.adaptive(minimum: 150, maximum: 300))]) {
                    ForEach(viewModel.achievements) { achievement in
                        AchievementView(achievement: achievement)
                            .cardView()
                    }
                }
            }
            .padding()
        }
        .navigationTitle(Text(LocalizedStringKey("profile"), bundle: .module))
    }
    
}

//
//  BannerViewModifier.swift
//  
//
//  Created by Nathan Fallet on 25/03/2023.
//

import SwiftUI

public struct BannerViewModifier: ViewModifier {
    
    @StateObject var viewModel = BannerViewModel()
    
    public init() {
        
    }
    
    public func body(content: Content) -> some View {
        content.overlay(
            VStack {
                if let type = viewModel.type {
                    VStack {
                        switch type {
                        case "experienceGained":
                            HStack {
                                Text("Experience gained!")
                                Spacer()
                                Text("Level \(viewModel.progressBarLevel ?? 0)")
                            }
                            ProgressView(
                                value: Double(viewModel.progressBarValue ?? 0),
                                total: Double(viewModel.progressBarTotal ?? 0)
                            )
                        default:
                            EmptyView()
                        }
                    }
                        .cardView()
                        .transition(AnyTransition.move(edge: .top).combined(with: .opacity))
                        .onAppear {
                            withAnimation {
                                DispatchQueue.main.asyncAfter(deadline: .now() + 3) {
                                    self.viewModel.type = nil
                                }
                            }
                        }
                    Spacer()
                }
            }
            .animation(.easeInOut)
        )
    }
    
}

struct BannerViewModifier_Preview: PreviewProvider {
    
    static var previews: some View {
        NavigationView {
            VStack {
                Button("Experience") {
                    NotificationCenter.default.post(name: .experienceGained, object: nil, userInfo: ["previousExperience": Experience(total: 1), "newExperience": Experience(total: 4)])
                }
                Button("Experience 2") {
                    NotificationCenter.default.post(name: .experienceGained, object: nil, userInfo: ["previousExperience": Experience(total: 4), "newExperience": Experience(total: 10)])
                }
            }
        }
        .modifier(BannerViewModifier())
    }
    
}

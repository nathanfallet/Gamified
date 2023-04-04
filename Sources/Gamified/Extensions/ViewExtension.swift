//
//  ViewExtension.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

extension View {
    
    func cardView<S: ShapeStyle>(background: S) -> some View {
        self
            .padding()
            .background(
                RoundedRectangle(cornerRadius: 10)
                    .fill(background)
                    .shadow(radius: 1)
            )
    }
    
    func cardView() -> some View {
        self.cardView(background: Color("CardColor"))
    }
    
}

public extension View {
    
    func addingGameBanner() -> some View {
        return self.modifier(BannerViewModifier())
    }
    
}

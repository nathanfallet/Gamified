//
//  CounterView.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

struct CounterView: View {
    
    let icon: String
    let text: String
    let count: Int

    var body: some View {
        HStack {
            Spacer(minLength: 0)
            VStack(spacing: 12) {
                Image(icon)
                    .resizable()
                    .frame(width: 44, height: 44)
                Text(text.localized().format(count))
            }
            Spacer(minLength: 0)
        }
    }
    
}

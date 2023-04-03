//
//  CounterView.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

public struct CounterView: View {
    
    let counter: Counter

    public var body: some View {
        HStack {
            Spacer(minLength: 0)
            VStack(spacing: 12) {
                Image(counter.icon)
                    .resizable()
                    .frame(width: 44, height: 44)
                Text(String(format: counter.text, locale: .current, arguments: [counter.count]))
            }
            Spacer(minLength: 0)
        }
    }
    
}

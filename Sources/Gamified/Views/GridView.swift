//
//  StatsGridView.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

struct GridView: View {
    
    let rows = 7
    var columns: Int { ((stats.count - 1) / rows) + 1 }
    
    let stats: [Stats]
    
    init(graphs: [Graph]) {
        let start = Date.previous17Weeks
        let end = Date()
        
        let raw = graphs.flatMap(\.stats)
        self.stats = start.nextStartOfWeek?.getDays(until: end)?.map({ day in
            raw.filter { $0.day == day.asString }.reduce(Stats(day: day.asString, value: 0)) {
                Stats(day: $0.day, value: $0.value + $1.value)
            }
        }) ?? []
    }
    
    var body: some View {
        HStack(spacing: 4) {
            ForEach(0 ..< columns, id: \.self) { column in
                VStack(spacing: 4) {
                    ForEach(0 ..< rows, id: \.self) { row in
                        color(row: row, column: column)
                            .cornerRadius(2)
                    }
                }
            }
        }
    }
    
    func color(row: Int, column: Int) -> Color? {
        let index = row + rows * column
        guard index < stats.count
        else { return .clear }
        
        guard let max = stats.map(\.value).max(), max != 0
        else { return color(for: 0) }
        
        return color(for: CGFloat(stats[index].value) / CGFloat(max))
    }
    
    func color(for level: CGFloat) -> Color {
        if level == 0 {
            return Color("AccentColorDisabled")
        } else if level <= 0.25 {
            return Color("AccentColorVeryLight")
        } else if level <= 0.5 {
            return Color("AccentColorLight")
        } else if level <= 0.75 {
            return Color("AccentColor")
        } else {
            return Color("AccentColorDark")
        }
    }
    
}

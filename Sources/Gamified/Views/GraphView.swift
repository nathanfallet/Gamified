//
//  StatsGraphView.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

public struct GraphView: View {
    
    // Properties
    
    var graph: Graph
    var graphData = [(Date, Int, CGFloat)]()
    var summedData = 0
    var hasData = false
    
    @State var currentIndex: Int?
    
    // Initializer
    
    public init(graph: Graph) {
        // Filter/fill data for 4 weeks
        let start = Date.previous4Weeks
        let end = Date()
        self.graph = Graph(
            title: graph.title,
            stats: start.getDays(until: end)?.map({ day in
                graph.stats.first(where: { $0.day == day.asString }) ?? Stats(day: day.asString, value: 0)
            }) ?? []
        )
        
        // Map to sets
        guard let max = self.graph.stats.map({ $0.value }).max(), max != 0
        else { return }
        self.graphData = self.graph.stats.map {(
            $0.day.asDate ?? .distantPast,
            $0.value,
            CGFloat($0.value) / CGFloat(max)
        )}
        
        // Sum data for label
        self.summedData = graphData.reduce(into: 0, { $0 += $1.1 })
        
        // Check if the graph has data
        self.hasData = !graphData.isEmpty
    }
    
    // Body
    public var body: some View {
        VStack {
            topSection
            graphSection
                .frame(minHeight: 100)
        }
    }
    
    // Top
    
    var topSection: some View {
        HStack {
            if let index = currentIndex {
                Text(graphData[index].0.rendered)
                Spacer()
                Text(String(graphData[index].1))
            } else {
                Text(graph.title)
                Spacer()
                Text(String(summedData))
            }
        }
    }
    
    // Graph
    
    var graphSection: some View {
        Group {
            if hasData {
                GeometryReader { reading in
                    HStack(alignment: .bottom, spacing: 0) {
                        ForEach(graphData, id: \.0) { element in
                            Capsule()
                                .frame(
                                    width: (reading.size.width / CGFloat(graphData.count)) * 0.7,
                                    height: reading.size.height * max(abs(element.2), 0.01)
                                )
                                .foregroundColor(
                                    element.2 > 0 ? Color("AccentColor") : Color("AccentColorDisabled")
                                )
                                .opacity(
                                    currentIndex != nil && graphData[currentIndex!].0 == element.0 ?
                                    0.7 : 1
                                )

                            if element.0 != graphData[graphData.count-1].0 {
                                Spacer()
                                    .frame(minWidth: 0)
                            }
                        }
                    }
                    .contentShape(Rectangle())
                    .highPriorityGesture(
                        DragGesture(minimumDistance: 20)
                            .onChanged { value in
                                let newIndex = Int((value.location.x / reading.size.width) * CGFloat(graphData.count))
                                if newIndex != currentIndex && newIndex < graphData.count && newIndex >= 0 {
                                    currentIndex = newIndex
                                    UISelectionFeedbackGenerator()
                                        .selectionChanged()
                                }
                            }
                            .onEnded { _ in
                                withAnimation(Animation.easeOut(duration: 0.2)) {
                                    currentIndex = nil
                                }
                            }
                    )
                }
            } else {
                Text(LocalizedStringKey("stats_graph_no_data"), bundle: .module)
                    .foregroundColor(.secondary)
            }
        }
    }
    
}

//
//  StatsView.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

public struct StatsView: View {
    
    @StateObject var viewModel: StatsViewModel
    
    /// Create a Stats view
    /// - Parameters:
    ///   - service: Gamified service used as source for data
    ///   - counters: Counters shown in the view
    ///   - isGridEnabled: If the grid should be shown in the view
    ///   - onAppear: Custom action called when the view appears (optional)
    public init(
        service: GamifiedService,
        counters: [Counter],
        isGridEnabled: Bool,
        onAppear: @escaping () -> Void = {}
    ) {
        self._viewModel = StateObject(wrappedValue: StatsViewModel(
            service: service,
            counters: counters,
            isGridEnabled: isGridEnabled,
            onAppear: onAppear
        ))
    }
    
    public var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                // Counters
                if !viewModel.counters.isEmpty {
                    Text(LocalizedStringKey("stats_counts"), bundle: .module)
                        .font(.title)
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 150, maximum: 300))]) {
                        ForEach(viewModel.counters, id: \.text) { counter in
                            CounterView(
                                icon: counter.icon,
                                text: counter.text,
                                count: counter.count
                            )
                            .cardView()
                        }
                    }
                }
                
                // Grid
                if viewModel.isGridEnabled {
                    Text(LocalizedStringKey("stats_grid"), bundle: .module)
                        .font(.title)
                        .padding(.top)
                    Text(LocalizedStringKey("stats_grid_description"), bundle: .module)
                        .padding(.vertical, 4)
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 300, maximum: 500))]) {
                        HStack {
                            Spacer(minLength: 0)
                            GridView(graphs: viewModel.graphs)
                                .frame(height: 136)
                                .frame(maxWidth: 336)
                            Spacer(minLength: 0)
                        }
                        .cardView()
                    }
                }
                
                // Graph
                if !viewModel.graphs.isEmpty {
                    Text(LocalizedStringKey("stats_graphs"), bundle: .module)
                        .font(.title)
                        .padding(.top)
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 300, maximum: 500))]) {
                        ForEach(viewModel.graphs, id: \.title) { graph in
                            GraphView(graph: graph)
                                .cardView()
                        }
                    }
                }
            }
            .padding()
        }
        .navigationTitle(Text(LocalizedStringKey("stats"), bundle: .module))
        .onAppear(perform: viewModel.onAppear)
    }
    
}

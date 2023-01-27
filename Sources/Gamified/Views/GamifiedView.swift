//
//  StatsView.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import SwiftUI

public struct GamifiedView: View {
    
    @StateObject var viewModel: GamifiedViewModel
    
    /// Create a Gamified view
    /// - Parameter configuration: The configuration for the view
    public init(configuration: GamifiedConfig) {
        self._viewModel = StateObject(wrappedValue: GamifiedViewModel(configuration: configuration))
    }
    
    public var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                // Counters
                if !viewModel.configuration.counters.isEmpty {
                    Text(LocalizedStringKey("stats_counts"), bundle: .module)
                        .font(.title)
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 150, maximum: 300))]) {
                        ForEach(viewModel.configuration.counters, id: \.text) { counter in
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
                if viewModel.configuration.isGridEnabled {
                    Text(LocalizedStringKey("stats_grid"), bundle: .module)
                        .font(.title)
                        .padding(.top)
                    Text(LocalizedStringKey("stats_grid_description"), bundle: .module)
                        .padding(.vertical, 4)
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 300, maximum: 500))]) {
                        HStack {
                            Spacer(minLength: 0)
                            GridView(graphs: viewModel.configuration.graphs)
                                .frame(height: 136)
                                .frame(maxWidth: 336)
                            Spacer(minLength: 0)
                        }
                        .cardView()
                    }
                }
                
                // Graph
                if !viewModel.configuration.graphs.isEmpty {
                    Text(LocalizedStringKey("stats_graphs"), bundle: .module)
                        .font(.title)
                        .padding(.top)
                    LazyVGrid(columns: [GridItem(.adaptive(minimum: 300, maximum: 500))]) {
                        ForEach(viewModel.configuration.graphs, id: \.title) { graph in
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

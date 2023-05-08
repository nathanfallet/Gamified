//
//  StatsWidgetProvider.swift
//  
//
//  Created by Nathan Fallet on 20/04/2023.
//

import WidgetKit
import SwiftUI
import Intents

public struct StatsWidgetProvider<Intent: INIntent>: IntentTimelineProvider {
    
    let service: GamifiedService
    let intent: Intent
    
    public init(service: GamifiedService, intent: Intent) {
        self.service = service
        self.intent = intent
    }
    
    public func placeholder(in context: Context) -> StatsWidgetEntry<Intent> {
        let start = context.family == .systemSmall ? Date.previous7Weeks : Date.previous17Weeks
        return StatsWidgetEntry(date: Date(), start: start, configuration: intent, graphs: [])
    }

    public func getSnapshot(for configuration: Intent, in context: Context, completion: @escaping (StatsWidgetEntry<Intent>) -> ()) {
        let start = context.family == .systemSmall ? Date.previous7Weeks : Date.previous17Weeks
        let end = Date()
        
        let stats: [Gamified.Stats] = start.nextStartOfWeek?.getDays(until: end)?.map({ day in
            return Gamified.Stats(day: day, value: Int.random(in: 0 ... 4))
        }) ?? []
        let graphs = [Graph(key: "", title: "", stats: stats)]
        
        let entry = StatsWidgetEntry(date: Date(), start: start, configuration: configuration, graphs: graphs)
        completion(entry)
    }

    public func getTimeline(for configuration: Intent, in context: Context, completion: @escaping (Timeline<StatsWidgetEntry<Intent>>) -> ()) {
        let start = context.family == .systemSmall ? Date.previous7Weeks : Date.previous17Weeks
        let end = Date()
        let graphs = service.getGraphs(startDate: start, endDate: end)

        let timeline = Timeline(entries: [
            StatsWidgetEntry(date: Date(), start: start, configuration: configuration, graphs: graphs)
        ], policy: .atEnd)
        completion(timeline)
    }
    
}

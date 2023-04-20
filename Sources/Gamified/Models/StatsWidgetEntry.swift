//
//  StatsWidgetEntry.swift
//  
//
//  Created by Nathan Fallet on 20/04/2023.
//

import Foundation
import WidgetKit

public struct StatsWidgetEntry<Intent>: TimelineEntry {
    
    public let date: Date
    public let start: Date
    public let configuration: Intent
    public let graphs: [Graph]
    
}

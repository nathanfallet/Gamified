//
//  DateExtension.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public extension Date {
    
    // Constants
    
    static var previous17Weeks: Date {
        Calendar.current.date(byAdding: .day, value: -118, to: Date()) ?? Date()
    }
    
    static var previous7Weeks: Date {
        Calendar.current.date(byAdding: .day, value: -48, to: Date()) ?? Date()
    }
    
    static var nextYear: Date {
        Calendar.current.date(byAdding: .day, value: 365, to: Date()) ?? Date()
    }
    
    // String for date
    
    var asString: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.string(from: self)
    }
    
    // Rendered date
    
    var rendered: String {
        let formatter = DateFormatter()
        formatter.dateStyle = .medium
        formatter.timeStyle = .none
        return formatter.string(from: self)
    }
    
    // Date enumeration
    
    var nextStartOfWeek: Date? {
        if Calendar.current.component(.weekday, from: self) == 1 {
            return self
        }
        return Calendar.current.date(byAdding: .day, value: 1, to: self)?.nextStartOfWeek
    }
    
    func getDays(until: Date) -> [Date]? {
        var date = Calendar.current.startOfDay(for: self)
        var result = [Date]()
        
        while date < until {
            result.append(date)
            if let nextDate = Calendar.current.date(byAdding: Calendar.Component.day, value: 1, to: date) {
                date = nextDate
            } else {
                return nil
            }
        }
        return result
    }
    
}

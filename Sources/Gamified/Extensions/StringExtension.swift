//
//  File.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

public extension String {
    
    // Date related
    
    var asDate: Date? {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.date(from: self)
    }
    
}

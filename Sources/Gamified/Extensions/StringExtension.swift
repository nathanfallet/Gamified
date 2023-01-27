//
//  File.swift
//  
//
//  Created by Nathan Fallet on 27/01/2023.
//

import Foundation

extension String {
    
    // Localization
    
    func localized(bundle: Bundle = .main, tableName: String = "Localizable") -> String {
        return NSLocalizedString(self, tableName: tableName, value: "**\(self)**", comment: "")
    }
    
    func format(_ args: CVarArg...) -> String {
        return String(format: self, locale: .current, arguments: args)
    }
    
    func format(_ args: [String]) -> String {
        return String(format: self, locale: .current, arguments: args)
    }
    
    // Date related
    
    var asDate: Date? {
        let formatter = DateFormatter()
        formatter.dateFormat = "yyyy-MM-dd"
        return formatter.date(from: self)
    }
    
}

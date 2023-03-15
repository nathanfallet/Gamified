//
//  File.swift
//  
//
//  Created by Nathan Fallet on 15/03/2023.
//

import Foundation
import SQLite

extension Expression where Datatype == String {
    
    var date: Expression<Date> {
        return Expression<Date>("date(\(template))", bindings)
    }
    
}

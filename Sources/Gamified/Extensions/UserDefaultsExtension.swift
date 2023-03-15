//
//  UserDefaultsExtension.swift
//  
//
//  Created by Nathan Fallet on 15/03/2023.
//

import Foundation

extension UserDefaults: GlobalStorageService {
    
    public func getValue(_ key: String) -> Int {
        integer(forKey: key)
    }
    
    public func setValue(_ key: String, value: Int) {
        set(value, forKey: key)
    }
    
}

//
//  ConnectionExtension.swift
//  GamifiedSQLite
//
//  Created by Nathan Fallet on 15/03/2023.
//

import Foundation
import Gamified
import SQLite

extension Connection: DailyStorageService {
    
    private var day: Expression<String> {
        Expression<String>("day")
    }
    
    private var value: Expression<Int> {
        Expression<Int>("value")
    }
    
    private func table(_ key: String) -> Table {
        Table("gamified_\(key)")
    }
    
    public func setupValue(_ key: String) {
        do {
            try run(table(key).create(ifNotExists: true) { table in
                table.column(day, primaryKey: true)
                table.column(value)
            })
        } catch {
            print(error.localizedDescription)
        }
    }
    
    public func getValue(_ key: String, for date: Date) -> Int {
        do {
            return try prepare(table(key).select(day, value).where(day == date.asString)).map { row in
                try row.get(value)
            }.first ?? 0
        } catch {
            print(error.localizedDescription)
        }
        return 0
    }
    
    public func getValues(_ key: String, startDate: Date, endDate: Date) -> [Date : Int] {
        do {
            return try prepare(table(key).select(day, value).where(day.date >= startDate && day.date <= endDate)).map { row in
                (try row.get(day).asDate ?? .distantPast, try row.get(value))
            }.reduce(into: [Date: Int](), { partialResult, tuple in
                partialResult[tuple.0] = tuple.1
            })
        } catch {
            print(error.localizedDescription)
        }
        return [:]
    }
    
    public func setValue(_ key: String, value: Int, for date: Date) {
        do {
            try run(table(key).upsert(
                day <- date.asString,
                self.value <- value,
                onConflictOf: day,
                set: [self.value <- value]
            ))
        } catch {
            print(error.localizedDescription)
        }
    }
    
}

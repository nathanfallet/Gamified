//
//  ExperienceTests.swift
//  
//
//  Created by Nathan Fallet on 25/03/2023.
//

import XCTest
@testable import Gamified

final class ExperienceTests: XCTestCase {
    
    func testExperienceLevel1() throws {
        let experience = Experience(total: 7)
        XCTAssertEqual(experience.level, 1)
        XCTAssertEqual(experience.current, 0)
        XCTAssertEqual(experience.toNextLevel, 9)
    }
    
    func testExperienceLevel10() throws {
        let experience = Experience(total: 161)
        XCTAssertEqual(experience.level, 10)
        XCTAssertEqual(experience.current, 1)
        XCTAssertEqual(experience.toNextLevel, 27)
    }
    
    func testExperienceLevel40() throws {
        let experience = Experience(total: 2930)
        XCTAssertEqual(experience.level, 40)
        XCTAssertEqual(experience.current, 10)
        XCTAssertEqual(experience.toNextLevel, 202)
    }
    
}

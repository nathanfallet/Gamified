// swift-tools-version: 5.7
// The swift-tools-version declares the minimum version of Swift required to build this package.

import PackageDescription

let package = Package(
    name: "Gamified",
    defaultLocalization: "en",
    platforms: [
        .iOS(.v14)
    ],
    products: [
        // Products define the executables and libraries a package produces, and make them visible to other packages.
        .library(
            name: "Gamified",
            targets: ["Gamified"]),
        .library(
            name: "GamifiedSQLite",
            targets: ["GamifiedSQLite"]),
    ],
    dependencies: [
        // Dependencies declare other packages that this package depends on.
        .package(url: "https://github.com/onevcat/Kingfisher.git", from: "7.0.0"),
        .package(url: "https://github.com/stephencelis/SQLite.swift.git", from: "0.14.1")
    ],
    targets: [
        // Targets are the basic building blocks of a package. A target can define a module or a test suite.
        // Targets can depend on other targets in this package, and on products in packages this package depends on.
        .target(
            name: "Gamified",
            dependencies: [
                .product(name: "Kingfisher", package: "Kingfisher")
            ],
            resources: [.process("Resources")]),
        .target(
            name: "GamifiedSQLite",
            dependencies: [
                .target(name: "Gamified"),
                .product(name: "SQLite", package: "SQLite.swift")
            ]),
        .testTarget(
            name: "GamifiedTests",
            dependencies: ["Gamified"]),
    ]
)

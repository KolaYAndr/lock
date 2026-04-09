//
//  Route.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

internal enum BoxesRoute: Hashable {
    case boxList
    case boxDetails(macAddress: String)
}

internal enum DocumentsRoute: Hashable {
    case documentList
}

internal enum SettingsRoute: Hashable {
    case settings
    case account
    case addPhoneNumber
    case changeEmail
    case emailChangedSuccessfully
    case warning
    case security
    case activeSessions
    case aboutApp
}

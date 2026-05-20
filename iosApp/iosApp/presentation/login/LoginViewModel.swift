//
//  LoginViewModel.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import Foundation
import Combine

internal enum LoginType: Int {
    case signIn
    case signUp
}

@MainActor
internal final class LoginViewModel: ObservableObject {
    @Published var loginType: LoginType = .signIn
    
    func toTypeAuth(_ type: LoginType) {
        loginType = type
    }
}

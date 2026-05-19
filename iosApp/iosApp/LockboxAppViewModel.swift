//
//  LockboxAppViewModel.swift
//  iosApp
//
//  Created by Николай on 09.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import Shared

internal class LockboxAppViewModel : ObservableObject {
    private let accountRepository = KoinHelper.shared.getAccountRepository()
    @Published private(set) var loginState: LoginState = .loading
    
    init() {
        let collector = DefaultFlowCollector(
            onValue: { [weak self] isLoggedIn in
                let state: LoginState = isLoggedIn ? .loggedIn : .notLoggedIn
                DispatchQueue.main.async { [weak self] in
                    self?.loginState = state
                }
            },
            defaultValue: false
        )
        accountRepository.loggedIn.collect(collector: collector) { error in
            self.loginState = .notLoggedIn
        }
    }
    
    func logOut() {
        Task {
            do {
                try await accountRepository.logOut()
            } catch {
                loginState = .notLoggedIn
            }
        }
    }
}

internal enum LoginState: Equatable {
    case loading
    case loggedIn
    case notLoggedIn
}

//
//  LockboxAppViewModel.swift
//  iosApp
//
//  Created by Николай on 09.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared

internal class LockboxAppViewModel : ObservableObject {
    private let accountRepository = KoinHelper.shared.getAccountRepository()
    @Published private(set) var loginState: LoginState = .loading

    init() {
        let collector = LoginStateCollector { [weak self] value in
            let isLoggedIn = (value as? Bool) ?? false
            let state: LoginState = isLoggedIn ? .loggedIn : .notLoggedIn
            DispatchQueue.main.async { [weak self] in
                self?.loginState = state
            }
        }
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

private final class LoginStateCollector: Kotlinx_coroutines_coreFlowCollector {
    private let onValue: (Any?) -> Void

    init(onValue: @escaping (Any?) -> Void) {
        self.onValue = onValue
    }

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        onValue(value)
        completionHandler(nil)
    }
}

internal enum LoginState: Equatable {
    case loading
    case loggedIn
    case notLoggedIn
}

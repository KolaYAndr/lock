//
//  WarningScreenViewModel.swift
//  iosApp
//
//  Created by Николай on 17.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared

@MainActor
internal struct WarningScreenViewModel {
    private let accountReposiory = KoinHelper.shared.getAccountRepository()
    
    func logOut() {
        Task {
            do {
                try await accountReposiory.logOut()
            }
        }
    }
}

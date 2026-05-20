//
//  ActiveSessionsViewModel.swift
//  iosApp
//
//  Created by Николай on 15.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import Shared
import Foundation

@MainActor
internal class ActiveSessionsViewModel: ObservableObject {
    private let accountRepository = KoinHelper.shared.getAccountRepository()
    @Published var currentIp: String = ""
    @Published var sessions: [Session] = []
    
    func getSessions() {
        Task { @MainActor in
            do {
                let activeSessions = try await accountRepository.getSessions()
                
                if let sessionSuccess = activeSessions as? ResponseSuccess<Sessions>,
                   let sessionList = sessionSuccess.data?.sessions {
                    sessions = sessionList.sorted { s1, s2 in
                        s1.lastActive > s2.lastActive
                    }
                }
            } catch {
                print(error)
                sessions = []
            }
        }
    }
    
    func closeOtherSessions() {
        Task {
            do {
                try await accountRepository.closeOtherSessions()
            }
        }
    }
}

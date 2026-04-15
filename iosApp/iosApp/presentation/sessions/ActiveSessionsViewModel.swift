//
//  ActiveSessionsViewModel.swift
//  iosApp
//
//  Created by Николай on 15.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared
import Foundation

@MainActor
internal class ActiveSessionsViewModel: ObservableObject {
    private let accountRepository = KoinHelper.shared.getAccountRepository()
    @Published var currentIp: String = ""
    @Published var sessions: [Session] = []
    
    func getSessions() {
        Task {
            do {
                let activeSessions = try await accountRepository.getSessions()
                let accountInfo = try await accountRepository.parseAccountInfo()
                
                if let sessionSuccess = activeSessions as? ResponseSuccess<Sessions>,
                   let accountInfoSuccess = accountInfo as? ResponseSuccess<AccountInfo> {
                    sessions = sessionSuccess.data?.sessions.filter { session in
                           session.sessionId != accountInfoSuccess.data?.sessionId
                       } ?? []
                    
                    let currentSession = sessionSuccess.data?.sessions.first { session in
                        session.sessionId == accountInfoSuccess.data?.sessionId
                    }
                    currentIp = currentSession?.ipAddress ?? ""
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

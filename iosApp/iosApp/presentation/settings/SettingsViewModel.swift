//
//  SettingsViewModel.swift
//  iosApp
//
//  Created by Николай on 12.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared

@MainActor
internal class SettingsViewModel: ObservableObject {
    private let accountRepository = KoinHelper.shared.getAccountRepository()
    @Published var email: String = ""
    @Published var fullName: String = ""
    private var sub: String = ""
    @Published var imageLink: String = ""
    
    init() {
        fetchInfo()
    }
    
    func fetchInfo() {
        Task { [weak self] in
            do {
                let response = try await self?.accountRepository.parseAccountInfo()
                
                if let success = response as? ResponseSuccess<AccountInfo> {
                    self?.email = success.data?.email ?? ""
                    self?.fullName = success.data?.fullName ?? ""
                    self?.sub = success.data?.sub ?? ""
                    
                    let imageLinkResponse = try await self?.accountRepository.getImageLink(sub: success.data?.sub ?? "")
                    self?.imageLink = imageLinkResponse ?? ""
                }
                
                if let failure = response as? ResponseFailure {
                    print(failure)
                }
            } catch {
                print("Failed to fetch account info: \(error)")
            }
        }
    }
    
    
    func refreshAccountInfo() async {
        do {
            let response = try await accountRepository.refreshAccount()
            
            if let success = response as? ResponseSuccess<AccountInfo> {
                email = success.data?.email ?? ""
                fullName = success.data?.fullName ?? ""
                sub = success.data?.sub ?? ""
                
                let imageLinkResponse = try await self.accountRepository.getImageLink(sub: success.data?.sub ?? "")
                imageLink = imageLinkResponse
            }
            
            if let failure = response as? ResponseFailure {
                print(failure)
            }
        } catch {
            print("Failed to refresh: \(error)")
        }
    }
    
    func logOut() {
        Task { [weak self] in
            do {
                try await self?.accountRepository.logOut()
            } catch {
                print("Logout failed: \(error)")
            }
        }
    }
}


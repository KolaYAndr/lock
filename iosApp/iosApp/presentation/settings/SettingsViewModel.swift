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
    @Published var imageLink: String = ""
    
    init() {
        fetchInfo()
    }
    
    func fetchInfo() {
        Task { @MainActor [weak self] in
            do {
                let response = try await self?.accountRepository.getProfileInfo()
                
                if let success = response as? ResponseSuccess<ProfileInfo> {
                    self?.email = success.data?.contactInfo.email ?? ""
                    self?.fullName = success.data?.legalInfo.fullName ?? ""
                }
                
                if let failure = response as? ResponseFailure {
                    print(failure)
                }
                
                let avatarResponse = try await self?.accountRepository.getImageLink()
                if let avatar = avatarResponse as? ResponseSuccess<Avatar>,
                   let downloadUrl = avatar.data?.downloadUrl {
                    self?.imageLink = downloadUrl
                }
            } catch {
                print("Failed to fetch account info: \(error)")
            }
        }
    }
    
    
    func refreshAccountInfo() async {
        do {
            let response = try await accountRepository.getProfileInfo()
            
            if let success = response as? ResponseSuccess<ProfileInfo> {
                email = success.data?.contactInfo.email ?? ""
                fullName = success.data?.legalInfo.fullName ?? ""
            }
            
            if let failure = response as? ResponseFailure {
                print(failure)
            }
            
            let avatarResponse = try await accountRepository.getImageLink()
            if let avatar = avatarResponse as? ResponseSuccess<Avatar>,
               let downloadUrl = avatar.data?.downloadUrl {
                imageLink = downloadUrl
            }
        } catch {
            print("Failed to refresh: \(error)")
        }
    }
    
    func logOut() {
        Task {
            do {
                try await accountRepository.logOut()
            } catch {
                print("Logout failed: \(error)")
            }
        }
    }
}


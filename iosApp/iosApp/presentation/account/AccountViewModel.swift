//
//  AccountViewModel.swift
//  iosApp
//
//  Created by Николай on 15.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Foundation
import Shared
import Combine

@MainActor
internal class AccountViewModel: ObservableObject {
    @Published var fullName: String = ""
    var imageLink: String = ""
    
    private let accountRepository: AccountRepository = KoinHelper.shared.getAccountRepository()
    
    func fetchInfo() {
        Task { [weak self] in
            do {
                let response = try await self?.accountRepository.getProfileInfo()
                
                if let success = response as? ResponseSuccess<ProfileInfo> {
                    self?.fullName = success.data?.legalInfo.fullName ?? ""
                }
                
                if let failure = response as? ResponseFailure {
                    print(failure)
                }
            } catch {
                print("Failed to fetch account info: \(error)")
            }
        }
    }
    
    func updatePersonalName() {
        Task {
            do {
                try await accountRepository.updateProfileInfo(email: nil, phoneNumber: nil, fullName: fullName, livingAddress: nil, passportIssuer: nil, passportNumber: nil, passportSeries: nil)
            }
        }
    }
    
    func refreshAccountInfo() async {
        do {
            let response = try await accountRepository.getProfileInfo()
            
            if let success = response as? ResponseSuccess<ProfileInfo> {
                fullName = success.data?.legalInfo.fullName ?? ""
            }
            
            if let failure = response as? ResponseFailure {
                print(failure)
            }
        } catch {
            print("Failed to refresh: \(error)")
        }
    }
    
    func sendImage(data: Data) {
        Task {
            do {
                let response = try await accountRepository.sendImage(data: KotlinByteArray.from(data: data))
                
                if let success = response as? ResponseSuccess<KotlinUnit> {
                    print(success)
                }
                
                if let failure = response as? ResponseFailure {
                    print(failure)
                }
            }
        }
    }
    
    func deleteAccount(
        onSuccess: @escaping () -> Void
    ) {
        Task {
            do {
                let response = try await accountRepository.deactivateUser()
                
                if let success = response as? ResponseSuccess<UpdateAccount> {
                    onSuccess()
                }
            }
        }
    }
    
    private func performUpdate(name: String, surname: String) {
        Task {
            do {
                try await accountRepository.updateInfo(
                    name: name,
                    surname: surname,
                    email: nil
                )
            }
        }
    }
}

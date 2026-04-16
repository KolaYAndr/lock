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
    @Published var name: String = ""
    @Published var surname: String = ""
    var imageLink: String = ""
    private var sub: String = ""
    
    private let accountRepository: AccountRepository = KoinHelper.shared.getAccountRepository()
    private var cancellables: Set<AnyCancellable> = []
    
    private let UPDATE_TIME_MS = 500
    
    init() {
        let nameSurnamePublisher: AnyPublisher<(String, String), Never> = Publishers.CombineLatest($name, $surname)
            .map { (name: String, surname: String) -> (String, String) in
                (name, surname)
            }
            .drop { name, surname in
                name.isEmpty || surname.isEmpty
            }
            .removeDuplicates { lhs, rhs in
                lhs.0 == rhs.0 && lhs.1 == rhs.1
            }
            .debounce(for: .milliseconds(UPDATE_TIME_MS),
                      scheduler: RunLoop.main)
            .eraseToAnyPublisher()
        
        nameSurnamePublisher
            .sink { [weak self] name, surname in
                self?.performUpdate(name: name, surname: surname)
            }
            .store(in: &cancellables)
    }
    
    func fetchInfo() {
        Task { [weak self] in
            do {
                let response = try await self?.accountRepository.parseAccountInfo()
                
                if let success = response as? ResponseSuccess<AccountInfo> {
                    self?.name = success.data?.givenName ?? ""
                    self?.surname = success.data?.familyName ?? ""
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
                name = success.data?.givenName ?? ""
                surname = success.data?.familyName ?? ""
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

//
//  LockboxDetailedViewModel.swift
//  iosApp
//
//  Created by Николай on 22.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Foundation
import Shared

@MainActor
internal class DetailedLockboxViewModel: ObservableObject {
    @Published var boxName: String = ""
    @Published var address: String = ""
    @Published var isBoxOpen: Bool = false
    @Published var signalStrength: Int = 0
    @Published var batteryLevel: Int = 0
    @Published var unbinded: Bool = false
    private var ownerSub: String = ""
    
    private let mac: String
    private let boxesRepository: BoxesRepository = KoinHelper.shared.getBoxesRepository()
    
    init(mac: String) {
        self.mac = mac
    }
    
    func findBoxByMac() {
        Task { @MainActor in
            do {
                let response = try await boxesRepository.getBoxByMac(address: mac)
                
                if let success = response as? ResponseSuccess<BoxWithStatus> {
                    boxName = success.data?.box.alias ?? ""
                    address = success.data?.box.address ?? ""
                    isBoxOpen = success.data?.status.opened?.boolValue ?? false
                    signalStrength = Int(success.data?.status.signalStrength?.doubleValue ?? 0)
                    batteryLevel = Int(success.data?.status.batteryLevel?.doubleValue ?? 0)
                    ownerSub = success.data?.box.ownerSub ?? ""
                }
            }
        }
    }
    
    func refreshInfo() async {
        do {
            let response = try await boxesRepository.getBoxByMac(address: mac)
            
            if let success = response as? ResponseSuccess<BoxWithStatus> {
                boxName = success.data?.box.alias ?? ""
                address = success.data?.box.address ?? ""
                isBoxOpen = success.data?.status.opened?.boolValue ?? false
                signalStrength = Int(success.data?.status.signalStrength?.doubleValue ?? 0)
                batteryLevel = Int(success.data?.status.batteryLevel?.doubleValue ?? 0)
                ownerSub = success.data?.box.ownerSub ?? ""
            }
        } catch {
            print(error)
        }
    }
    
    func unbindDevice() {
        Task { @MainActor in
            do {
                let response = try await boxesRepository.unbindBox(address: mac)
                
                if let success = response as? ResponseSuccess<KotlinUnit> {
                    unbinded = true
                }
            }
        }
    }
    
    func saveAddress() {
        Task { @MainActor in
            do {
                let response = try await boxesRepository.updateBoxAddress(macAddress: mac, newAddress: address, ownerSub: ownerSub)
                
                if let success = response as? ResponseSuccess<UpdateBox>,
                   let newAddress = success.data?.address {
                    address = newAddress
                }
            }
        }
    }
    
    func saveName() {
        Task { @MainActor in
            do {
                let response = try await boxesRepository.updateBoxName(macAddress: mac, newName: boxName, ownerSub: ownerSub)
                
                if let success = response as? ResponseSuccess<UpdateBox>,
                   let newName = success.data?.alias {
                    boxName = newName
                }
            }
        }
    }
    
    func openBox() {
        Task { @MainActor in
            do {
                let response = try await boxesRepository.openBoxByMac(address: mac)
                
                if let success = response as? ResponseSuccess<OpenBox> {
                    isBoxOpen = true
                }
            }
        }
    }
}

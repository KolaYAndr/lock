//
//  LockboxesViewModel.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Combine
import Shared

@MainActor
internal class LockboxesViewModel: ObservableObject {
    private let boxesRepository = KoinHelper.shared.getBoxesRepository()
    @Published private(set) var boxes: [BoxWithStatus] = [BoxWithStatus(box: Box(address: "Doma", alias: "U sebya", ownerSub: "", macAddress: "1:2:3"), status: Status(macAddress: "1:2:3", opened: false, batteryLevel: 100, signalStrength: 100, timeStamp: nil)), BoxWithStatus(box: Box(address: "Doma", alias: "U sebya", ownerSub: "", macAddress: "1:2:4"), status: Status(macAddress: "1:2:4", opened: false, batteryLevel: 100, signalStrength: 100, timeStamp: nil))]
    
//    init() {
//        let collector = DefaultFlowCollector<[BoxWithStatus]>(
//            onValue: { [weak self] boxes in
//                DispatchQueue.main.async { [weak self] in
//                    self?.boxes = boxes
//                }
//            },
//            defaltValue: []
//        )
//        boxesRepository.getBoxesPagingFlow().collect(collector: collector) { error in
//            print(error)
//            self.boxes = []
//        }
//    }
}

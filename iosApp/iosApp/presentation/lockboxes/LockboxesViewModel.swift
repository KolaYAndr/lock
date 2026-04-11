//
//  LockboxesViewModel.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Combine
import Shared

internal class LockboxesViewModel: ObservableObject {
    private let boxesRepository = KoinHelper.shared.getBoxesRepository()
    @Published var boxes: [BoxWithStatus] = []
//    [IdentifiebleBox(box: Box(address: "Doma", alias: "U sebya", ownerSub: "", macAddress: "1:2:3")), IdentifiebleBox(box: Box(address: "Doma", alias: "U sebya", ownerSub: "", macAddress: "1:2:4"))]
    
    init() {
        let collector = DefaultFlowCollector<[BoxWithStatus]>(
            onValue: { [weak self] boxes in
                DispatchQueue.main.async { [weak self] in
                    self?.boxes = boxes
                }
            },
            defaltValue: []
        )
        boxesRepository.getBoxesPagingFlow().collect(collector: collector) { error in
            print(error)
            self.boxes = []
        }
    }
    
}

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
    @Published var boxes: [IdentifiebleBox] = [IdentifiebleBox(box: Box(address: "Doma", alias: "U sebya", ownerSub: "", macAddress: "1:2:3")), IdentifiebleBox(box: Box(address: "Doma", alias: "U sebya", ownerSub: "", macAddress: "1:2:4"))]
    
//    init() {
//        let collector = DefaultFlowCollector(
//            onValue: { [weak self] boxes in
//                let idBoxes: [IdentifiebleBox] = boxes.map { box in
//                    let idBox = box as! Box
//                    return IdentifiebleBox(box: idBox)
//                }
//                DispatchQueue.main.async { [weak self] in
//                    self?.boxes = idBoxes
//                }
//            },
//            defaltValue: []
//        )
//        boxesRepository.getBoxesPagingFlow().collect(collector: collector) { _ in
//            self.boxes = []
//        }
//    }
    
}

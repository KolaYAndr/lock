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
    @Published var boxes: [BoxWithStatus] = []

    init() {
        observeBoxes()
    }

    private func observeBoxes() {
        let collector = DefaultFlowCollector<[BoxWithStatus]>(
            onValue: { [weak self] snapshot in
                let items = snapshot as [BoxWithStatus]

                DispatchQueue.main.async {
                    self?.boxes = items
                }
            },
            defaultValue: []
        )

        boxesRepository.getBoxesSnapshot().collect(collector: collector) { error in
            if let error {
                print(error)
            }
        }
    }
}

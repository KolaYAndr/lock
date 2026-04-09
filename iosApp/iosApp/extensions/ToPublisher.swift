//
//  ToPublisher.swift
//  iosApp
//
//  Created by Николай on 08.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared
import Combine

extension Kotlinx_coroutines_coreFlow {
    func toPublisher<T: AnyObject>() -> AnyPublisher<T, Never> {
        let swiftFlow = SwiftFlow<T>(kotlinFlow: self)
        return Deferred<Publishers.HandleEvents<PassthroughSubject<T, Never>>> {
            let subject = PassthroughSubject<T, Never>()
            let cancelable = swiftFlow.watch { next in
                if let next = next {
                    subject.send(next)
                }
            }
            return subject.handleEvents(receiveCancel: {
                cancelable.cancel()
            })
        }
        .eraseToAnyPublisher()
    }
}

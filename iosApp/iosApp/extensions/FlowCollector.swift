//
//  FlowCollector.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared

typealias FlowCollector = Kotlinx_coroutines_coreFlowCollector

internal final class FlowStateCollector: FlowCollector {
    private let onValue: (Any?) -> Void

    init(onValue: @escaping (Any?) -> Void) {
        self.onValue = onValue
    }

    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        onValue(value)
        completionHandler(nil)
    }
}

internal final class DefaultFlowCollector<T>: FlowCollector {
    private let onValue: (T) -> Void
    private let defaultValue: T
    
    init(onValue: @escaping (T) -> Void, defaltValue: T) {
        self.onValue = onValue
        self.defaultValue = defaltValue
    }
    
    func emit(value: Any?, completionHandler: @escaping (Error?) -> Void) {
        if let typed = value as? T {
            onValue(typed)
        } else {
            onValue(defaultValue)
        }
        completionHandler(nil)
    }
}

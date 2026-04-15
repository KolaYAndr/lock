//
//  DataToKotlinByteArray.swift
//  iosApp
//
//  Created by Николай on 15.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared

extension KotlinByteArray {
    static func from(data: Data) -> KotlinByteArray {
        let array = KotlinByteArray(size: Int32(data.count))
        data.withUnsafeBytes { buffer in
            guard let ptr = buffer.baseAddress?.assumingMemoryBound(to: Int8.self) else { return }
            for i in 0..<data.count {
                array.set(index: Int32(i), value: ptr[i])
            }
        }
        return array
    }
}

//
//  Box.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import Shared

internal class IdentifiebleBox: Identifiable {
    let box: Box
    let id: String
    
    init(box: Box) {
        self.box = box
        self.id = box.macAddress
    }
}

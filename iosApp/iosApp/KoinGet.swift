//
//  KoinGet.swift
//  iosApp
//
//  Created by Николай on 14.09.2025.
//  Copyright © 2025 LockBox. All rights reserved.
//

import Shared

func get<A: AnyObject>() -> A {
    DiHelper.shared.koin.get(objCClass: A.self) as! A
}

func get<A: AnyObject>(_ type: A.Type) -> A {
    return DiHelper.shared.koin.get(objCClass: A.self) as! A
}

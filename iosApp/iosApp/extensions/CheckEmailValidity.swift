//
//  CheckEmailValidity.swift
//  iosApp
//
//  Created by Николай on 23.03.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import Foundation

extension String {
    var isValidEmail: Bool {
        let pattern = #"^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,}$"#
        let regex = try? NSRegularExpression(pattern: pattern)
        let range = NSRange(startIndex..., in: self)
        return regex?.firstMatch(in: self, options: [], range: range) != nil
    }
}

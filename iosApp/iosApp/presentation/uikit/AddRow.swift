//
//  AddRow.swift
//  iosApp
//
//  Created by Николай on 14.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct AddRow: View {
    private let title: String
    
    init(title: String) {
        self.title = title
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 8) {
            Image(.plus)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(.green500)
            
            Text(title)
                .headlineLarge()
                .foregroundStyle(.green500)
                .lineLimit(1)
        }
    }
}

#Preview {
    AddRow(title: String(localized: "add_device"))
}

//
//  TitledTopWithCloseAndBackButtons.swift
//  iosApp
//
//  Created by Николай on 08.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct TitledTopWithCloseAndBackButtons : View {
    private let title: String
    private let onClose: () -> Void
    
    init(title: String, onClose: @escaping () -> Void) {
        self.title = title
        self.onClose = onClose
    }
    
    var body: some View {
        HStack {
            Image(.arrowBack)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .padding(.trailing, 12)
                .foregroundStyle(.grey500)
                .onTapGesture {
                    onClose()
                }
            
            Text(title)
                .titleSmall()
                .foregroundStyle(.grey100)
                .frame(maxWidth: .infinity, alignment: .center)
                .padding(.vertical, 12)
            
            Image(.close)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .padding(.leading, 12)
                .foregroundStyle(.grey500)
                .onTapGesture {
                    onClose()
                }
        }.padding(.horizontal, 16)
    }
}

#Preview {
    TitledTopWithCloseAndBackButtons(title: "Title", onClose: {})
}

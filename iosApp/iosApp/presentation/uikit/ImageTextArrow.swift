//
//  ImageTextArrow.swift
//  iosApp
//
//  Created by Николай on 08.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct ImageTextArrow : View {
    private let text: String
    private let imageRes: ImageResource
    private let onClick: () -> Void
    
    init(text: String, imageRes: ImageResource, onClick: @escaping () -> Void) {
        self.text = text
        self.imageRes = imageRes
        self.onClick = onClick
    }
    
    var body: some View {
        HStack(spacing: 12) {
            Image(imageRes)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .tint(.grey100)
            
            Text(text)
                .headlineLarge()
                .foregroundStyle(.white)
                .padding(.vertical, 10)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Image(.chevronRight)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .tint(.grey100)
        }.onTapGesture {
            onClick()
        }
    }
}

#Preview {
    ImageTextArrow(text: "TG", imageRes: .eye, onClick: {})
}

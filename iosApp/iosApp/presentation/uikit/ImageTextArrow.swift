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
    
    init(text: String, imageRes: ImageResource) {
        self.text = text
        self.imageRes = imageRes
    }
    
    var body: some View {
        HStack(spacing: 12) {
            Image(imageRes)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(.grey100)
            
            Text(text)
                .headlineLarge()
                .foregroundStyle(.white)
                .padding(.vertical, 10)
                .frame(maxWidth: .infinity, alignment: .leading)
            
            Image(.chevronRight)
                .resizable()
                .scaledToFit()
                .frame(width: 20, height: 20)
                .foregroundStyle(.grey700)
        }
    }
}

#Preview {
    ImageTextArrow(text: "TG", imageRes: .eye)
}

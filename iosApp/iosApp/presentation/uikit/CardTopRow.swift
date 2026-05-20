//
//  CardTopRow.swift
//  iosApp
//
//  Created by Николай on 03.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct CardTopRow : View {
    private let currentStep: Int
    private let totalSteps: Int
    private let onClose: () -> Void
    private let onBack: () -> Void
    
    init(currentStep: Int, totalSteps: Int, onClose: @escaping () -> Void, onBack: @escaping () -> Void) {
        self.currentStep = currentStep
        self.totalSteps = totalSteps
        self.onClose = onClose
        self.onBack = onBack
    }
    
    var body: some View {
        VStack {
            HStack {
                Image(.arrowBack)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
                    .onTapGesture(perform: onBack)
                    .foregroundStyle(.grey500)
                    .padding(.trailing, 12)
                
                VStack(alignment: .leading, spacing: 2) {
                    Text(String(localized: "register"))
                        .titleSmall()
                        .foregroundStyle(.grey100)
                    
                    Text(String(format: NSLocalizedString("step_of", comment: ""), currentStep, totalSteps))
                        .subHeadlineLarge()
                        .foregroundStyle(.grey500)
                }.padding(.vertical, 12)
                .frame(maxWidth: .infinity, alignment: .leading)
                
                Image(.close)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
                    .onTapGesture(perform: onClose)
                    .foregroundStyle(.grey500)
                    .padding(.leading, 12)
            }.padding(.horizontal, 16)
            LockboxHorizontalDivider()
        }
    }
}

#Preview {
    CardTopRow(currentStep: 1, totalSteps: 3, onClose: {}, onBack: {})
}

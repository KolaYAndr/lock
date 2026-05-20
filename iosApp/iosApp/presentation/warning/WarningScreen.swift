//
//  WarningScreen.swift
//  iosApp
//
//  Created by Николай on 17.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct WarningScreen: View {
    private let viewModel = WarningScreenViewModel()
    
    var body: some View {
        VStack(alignment: .center) {
            Image(.alert)
                .resizable()
                .scaledToFit()
                .frame(width: 48, height: 48)
                .foregroundStyle(.grey500)
            
            Text(String(localized: "your_account_will_be_deleted"))
                .titleMedium()
                .foregroundStyle(.grey100)
                .multilineTextAlignment(.center)
                .padding(.top, 12)
            
            Text(String(localized: "deletion_in_couple_of_days"))
                .bodyLarge()
                .foregroundStyle(.grey500)
                .multilineTextAlignment(.center)
                .padding(.top, 8)
            
            Button {
                viewModel.logOut()
            } label: {
                Text(String(localized: "ready"))
                    .bodyMedium()
                    .foregroundStyle(.grey900)
            }
            .buttonStyle(.grey)
            .padding(.top, 16)
        }
        .padding(.horizontal, 32)
        .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
        .toolbar(.hidden)
        .navigationBarBackButtonHidden()
    }
}

#Preview {
    WarningScreen()
}

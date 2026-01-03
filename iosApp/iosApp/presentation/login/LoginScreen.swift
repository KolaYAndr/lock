//
//  LoginScreen.swift
//  iosApp
//
//  Created by Николай on 02.01.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct LoginScreen : View {
    @State private var showModal = false

    var body: some View {
        ZStack {
            Color.grey950.ignoresSafeArea()

            VStack(alignment: .center) {
                Text("Log in your profile")
                    .titleMedium()
                    .foregroundStyle(.grey100)
                    .multilineTextAlignment(.center)
                    .padding(.top, 12)

                Text("Start using today")
                    .bodyLarge()
                    .foregroundStyle(.grey500)
                    .multilineTextAlignment(.center)
                    .padding(.top, 8)

                Button {
                    showModal = true
                } label: {
                    Text("Sign in")
                        .bodyMedium()
                        .foregroundStyle(.grey900)
                }
                .buttonStyle(.borderedProminent)
                .tint(.green500)
                .buttonBorderShape(.roundedRectangle(radius: 8))
                .padding(.top, 16)
            }
            .padding(.horizontal, 32)
            .sheet(isPresented: $showModal) {
                
            }
        }
    }
}

struct LoginPromptView_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}

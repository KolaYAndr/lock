//
//  LoginScreen.swift
//  iosApp
//
//  Created by Николай on 02.01.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct LoginScreen: View {
    @State private var showModal = false
    
    var body: some View {
        ZStack {
            Color.grey950.ignoresSafeArea()
            
            VStack(alignment: .center) {
                Image(.logIn)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 48, height: 48)
                    .tint(.grey500)
                
                Text(String(localized: "log_in_your_profile"))
                    .titleMedium()
                    .foregroundStyle(.grey100)
                    .multilineTextAlignment(.center)
                    .padding(.top, 12)
                
                Text(String(localized: "start_using_today"))
                    .bodyLarge()
                    .foregroundStyle(.grey500)
                    .multilineTextAlignment(.center)
                    .padding(.top, 8)
                
                Button {
                    showModal = true
                } label: {
                    Text(String(localized: "sign_in"))
                        .bodyMedium()
                        .foregroundStyle(.grey900)
                }
                .buttonStyle(.greenBackground)
                .padding(.top, 16)
            }
            .padding(.horizontal, 32)
            .sheet(isPresented: $showModal) {
                LoginBottomSheet()
                    .presentationDetents([.medium, .large])
                    .presentationDragIndicator(.visible)
            }
        }
    }
}

internal struct LoginBottomSheet: View {
    @Environment(\.dismiss) private var dismiss
    @ObservedObject var viewModel : LoginViewModel = LoginViewModel()
    @State private var previousType: LoginType = .signIn
    
    private func currentTransition(for target: LoginType) -> AnyTransition {
        return if target.rawValue > previousType.rawValue {
            forwardTransition
        } else {
            backwardTransition
        }
    }
    
    var body : some View {
        ZStack {
            Color.grey900.ignoresSafeArea()
            
            switch viewModel.loginType {
                
            case .signIn: SignInCard(
                onDismiss: { dismiss() },
                toSignUp: { viewModel.toTypeAuth(.signUp) },
            ).transition(currentTransition(for: .signUp))
                
            case .signUp: SignUpCard(
                toSignIn: { viewModel.toTypeAuth(.signIn) }
            ).transition(currentTransition(for: .signIn))
                
            }
        }
    }
}

struct LoginPromptView_Previews: PreviewProvider {
    static var previews: some View {
        LoginScreen()
    }
}

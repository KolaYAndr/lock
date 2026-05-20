//
//  LoginScreen.swift
//  iosApp
//
//  Created by Николай on 02.01.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct LoginScreen: View {
    @State private var showModal = false
    
    var body: some View {
        ZStack {
            Color.grey950.ignoresSafeArea()
            
            EmptyScreenTemplate(
                imageResource: .logIn,
                title: String(localized: "log_in_your_profile"),
                subtitle: String(localized: "start_using_today"),
                buttonText: String(localized: "sign_in"),
                action: { showModal = true }
            )
            .sheet(isPresented: $showModal) {
                LoginBottomSheet()
                    .presentationDetents([.fraction(0.7), .large])
                    .presentationDragIndicator(.visible)
            }
        }
    }
}

internal struct LoginBottomSheet: View {
    @Environment(\.dismiss) private var dismiss
    @ObservedObject var viewModel : LoginViewModel = LoginViewModel()
    
    private func currentTransition(for target: LoginType) -> AnyTransition {
        return if target.rawValue > LoginType.signIn.rawValue {
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
                toSignIn: { viewModel.toTypeAuth(.signIn) },
                onClose: { dismiss() }
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

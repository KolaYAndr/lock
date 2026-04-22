//
//  Untitled.swift
//  iosApp
//
//  Created by Николай on 07.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI
import Foundation

internal struct ConfirmEmailStep : View {
    private let onChangeEmail: () -> Void
    private let sendEmailConfirmation: () -> Void
    private let startPollingEmailConfirmation: () -> Void
    private let stopPollingEmailConfirmation: () -> Void
    private let email: String
    
    @State private var showHelpModalSheet = false
    
    init(onChangeEmail: @escaping () -> Void, sendEmailConfirmation: @escaping () -> Void, startPollingEmailConfirmation: @escaping () -> Void, stopPollingEmailConfirmation: @escaping () -> Void, email: String) {
        self.onChangeEmail = onChangeEmail
        self.email = email
        self.sendEmailConfirmation = sendEmailConfirmation
        self.startPollingEmailConfirmation = startPollingEmailConfirmation
        self.stopPollingEmailConfirmation = stopPollingEmailConfirmation
    }
    
    var body : some View {
        Text(String(localized: "check_your_email"))
            .titleMedium()
            .foregroundStyle(.white)
            .multilineTextAlignment(.center)
            .padding(.top, 16)
        
        Text(String(localized: "we_sent_message_to_indicated_email"))
            .bodyMedium()
            .foregroundStyle(.grey500)
            .multilineTextAlignment(.center)
            .padding(.top, 8)
        
        HStack(alignment: .center, spacing: 6) {
            Text(email)
                .headlineMedium()
                .foregroundStyle(.grey100)
                .multilineTextAlignment(.center)
                .padding(.vertical, 6)
            
            Text(String(localized: "change_short"))
                .subHeadlineLarge()
                .foregroundStyle(.grey100)
        }
        .padding(.top, 8)
        .onTapGesture {
            onChangeEmail()
        }
        .task {
            sendEmailConfirmation()
            startPollingEmailConfirmation()
        }
        .onDisappear {
            stopPollingEmailConfirmation()
        }
        
        LockboxCountDownButton(startTime: 60, onSendAgain: sendEmailConfirmation)
            .padding(.vertical, 12)
            .frame(maxWidth: .infinity, alignment: .center)
        
        LockboxHelpButton(action: { showHelpModalSheet.toggle() })
            .frame(maxWidth: .infinity, alignment: .center)
            .sheet(isPresented: $showHelpModalSheet) {
                HelpModalSheet()
            }
    }
}

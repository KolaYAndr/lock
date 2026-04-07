//
//  L.swift
//  iosApp
//
//  Created by Николай on 07.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct LockboxCountDownButton : View {
    private let timer = Timer.publish(every: 1, on: .main, in: .common).autoconnect()
    
    @State private var timeRemaining: Int
    @State private var timerRunning = false
    
    private let onSendAgain: () -> Void
    
    init(startTime: Int, onSendAgain: @escaping () -> Void) {
        self.timeRemaining = startTime
        self.onSendAgain = onSendAgain
    }
    
    var body: some View {
        Button {
            onSendAgain()
        } label: {
            if timeRemaining > 0 {
                let minutes = timeRemaining / 60
                let seconds = timeRemaining % 60
                
                Text(String(format: NSLocalizedString("before_sending_one_more_time", comment: ""), minutes, seconds))
                    .frame(maxWidth: .infinity)
            } else {
                Text(String(localized: "send_one_more_time"))
                    .padding(14)
                    .frame(maxWidth: .infinity)
            }
        }
        .disabled(timeRemaining != 0)
        .buttonStyle(.lighterGrey)
        .onAppear() {
            timerRunning = true
        }
        .onReceive(timer) { _ in
            if timeRemaining > 0 {
                timeRemaining -= 1
            } else {
                timerRunning = false
            }
        }
        
    }
}

#Preview {
    LockboxCountDownButton(startTime: 10, onSendAgain: {})
}

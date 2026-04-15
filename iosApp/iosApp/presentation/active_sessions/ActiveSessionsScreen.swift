//
//  ActiveSessionsScreen.swift
//  iosApp
//
//  Created by Николай on 15.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI
import UIKit

internal struct ActiveSessionsScreen: View {
    private let deviceName = UIDevice.current.name
    @StateObject private var viewModel = ActiveSessionsViewModel()
    
    var body: some View {
        ScrollView {
            VStack(alignment: .leading) {
                Text(String(localized: "this_device"))
                    .subHeadlineLarge()
                    .foregroundStyle(.grey500)
                VStack(alignment: .leading, spacing: 0) {
                    Device(
                        title: deviceName,
                        subtitle: String(format: NSLocalizedString("location_dot_access_time", comment: ""), viewModel.currentIp),
                        imageResource: .smartphone
                    )
                    
                    LockboxHorizontalDivider()
                    
                    Button {
                        viewModel.closeOtherSessions()
                    } label: {
                        Text(String(localized: "finish_all_other_sessions"))
                            .headlineLarge()
                            .foregroundStyle(.red600)
                            .padding(4)
                    }
                    .buttonStyle(.plain)
                    .padding(.vertical, 10)
                }
                .padding(.horizontal, 16)
                .background(.grey900)
                .clipShape(.roundedCornerShape8)
                .padding(.top, 6)
                
                Text(String(localized: "active_sessions"))
                    .subHeadlineLarge()
                    .foregroundStyle(.grey500)
                    .padding(.top, 24)
                    .padding(.bottom, 6)
                
                LazyVStack {
                    ForEach(viewModel.sessions, id: \.sessionId) { session in
                        Device(
                            title: String(localized: "unknown_device"),
                            subtitle: session.ipAddress,
                            imageResource: .monitorSmartphone
                        )
                        .frame(maxWidth: .infinity, alignment: .leading)
                    }
                }
                .padding(.horizontal, 16)
                .background(.grey900)
                .clipShape(.roundedCornerShape8)
            }
            .onAppear {
                viewModel.getSessions()
            }
        }
        .refreshable {
            viewModel.getSessions()
        }
        .navigationTitle(Text(String(localized: "active_sessions")))
        .padding(.horizontal, 16)
        .padding(.vertical, 12)
    }
}

private struct Device: View {
    private let title: String
    private let subtitle: String
    private let imageResource: ImageResource
    
    init(title: String, subtitle: String, imageResource: ImageResource) {
        self.title = title
        self.subtitle = subtitle
        self.imageResource = imageResource
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 12) {
            Image(imageResource)
                .resizable()
                .scaledToFit()
                .frame(width: 28, height: 28)
            
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .headlineLarge()
                    .foregroundStyle(.white)
                
                Text(subtitle)
                    .footnote()
                    .foregroundStyle(.grey500)
            }
            .padding(.vertical, 10)
        }
    }
}

#Preview {
    ActiveSessionsScreen()
}

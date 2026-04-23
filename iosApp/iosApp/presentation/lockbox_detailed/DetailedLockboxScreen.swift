//
//  DetailedLockboxScreen.swift
//  iosApp
//
//  Created by Николай on 22.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct DetailedLockboxScreen: View {
    private var mac: String
    @Environment(\.dismiss) private var dismiss
    
    @StateObject private var viewModel: DetailedLockboxViewModel
    
    @State private var showOpenBoxDialog = false
    @State private var showUnbindBoxDialog = false
    @State private var showAddressChangeModal = false
    @State private var showNameChangeModal = false
    
    init(mac: String) {
        self.mac = mac
        self._viewModel = StateObject(wrappedValue: DetailedLockboxViewModel(mac: mac))
    }
    
    var body: some View {
        ScrollView {
            VStack {
                Image(viewModel.isBoxOpen ? .lockOpen : .lock)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 64, height: 64)
                    .padding(.top, 48)
                
                Text(viewModel.boxName)
                    .titleMedium()
                    .foregroundStyle(.grey100)
                    .padding(.top, 12)
                
                Text(viewModel.address)
                    .bodyLarge()
                    .foregroundStyle(.grey500)
                    .padding(.top, 8)
                
                Button {
                    showOpenBoxDialog = true
                } label: {
                    Text(String(localized: "open"))
                        .headlineMedium()
                }
                .buttonStyle(.greenBackground)
                .padding(.top, 16)
                
                LockboxGroup {
                    Property(
                        title: String(localized: "device_address"),
                        subtitle: viewModel.address
                    )
                    .onTapGesture {
                        showAddressChangeModal = true
                    }
                    
                    Property(
                        title: String(localized: "device_name"),
                        subtitle: viewModel.boxName
                    )
                    .onTapGesture {
                        showAddressChangeModal = true
                    }
                }
                .padding(.top, 24)
                .padding(.horizontal, 16)
                
                Button {
                    showUnbindBoxDialog = true
                } label: {
                    Text(String(localized: "unbind_device"))
                        .headlineLarge()
                }
                .buttonStyle(.darkRed)
                .padding(.top, 24)
            }
            .sheet(isPresented: $showNameChangeModal) {
                
            }
            .alert(isPresented: $showOpenBoxDialog) {
                Alert(
                    title: Text(String(localized: "open_device_question")),
                    primaryButton: .cancel(
                        Text(String(localized: "cancel")),
                        action: { showOpenBoxDialog = false }
                    ),
                    secondaryButton: .default(
                        Text(String(localized: "open")),
                        action: { viewModel.openBox() }
                    )
                )
            }
            .alert(isPresented: $showUnbindBoxDialog) {
                Alert(
                    title: Text(String(localized: "unbind_device_question")),
                    message: Text(String(localized: "not_cancellable_unbind_device")),
                    primaryButton: .cancel(
                        Text(String(localized: "cancel")),
                        action: { showUnbindBoxDialog = false }
                    ),
                    secondaryButton: .destructive(
                        Text(String(localized: "unbind")),
                        action: { viewModel.unbindDevice() }
                    )
                )
            }
            .onChange(of: viewModel.unbinded) {
                dismiss()
            }
            .onAppear {
                viewModel.findBoxByMac()
            }
            .refreshable {
                await viewModel.refreshInfo()
            }
            .navigationTitle($viewModel.boxName)
        }
    }
}

private struct Property: View {
    private let title: String
    private let subtitle: String
    
    init(title: String, subtitle: String) {
        self.title = title
        self.subtitle = subtitle
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 12) {
            VStack(alignment: .leading, spacing: 2) {
                Text(title)
                    .headlineLarge()
                    .foregroundStyle(.white)
                
                Text(subtitle)
                    .footnote()
                    .foregroundStyle(.grey500)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            
            Image(.chevronRight)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(.grey500)
        }
        .frame(maxWidth: .infinity)
        .padding(.vertical, 10)
        .padding(.horizontal, 16)
    }
}

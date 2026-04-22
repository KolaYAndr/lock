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
    @StateObject private var viewModel: DetailedLockboxViewModel
    @Environment(\.dismiss) private var dismiss
    
    init(mac: String) {
        self.mac = mac
        self._viewModel = StateObject(wrappedValue: DetailedLockboxViewModel(mac: mac))
    }
    
    var body: some View {
        ScrollView {
            VStack {
                Text(viewModel.boxName)
                    .titleMedium()
                    .foregroundStyle(.grey100)
                    .padding(.top, 12)
                
                Text(viewModel.address)
                    .bodyLarge()
                    .foregroundStyle(.grey500)
                    .padding(.top, 8)
                
                Button {
                    viewModel.openBox()
                } label: {
                    Text(String(localized: "open"))
                        .headlineMedium()
                }
                .buttonStyle(.greenBackground)
                .padding(.top, 16)
                
                Button {
                    viewModel.unbindDevice()
                } label: {
                    Text(String(localized: "unbind_device"))
                        .headlineLarge()
                }
                .buttonStyle(.darkRed)
                .padding(.top, 24)
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

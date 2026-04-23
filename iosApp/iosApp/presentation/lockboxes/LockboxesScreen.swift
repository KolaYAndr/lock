//
//  LockboxesScreen.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI
import Shared

internal struct LockboxesScreen: View {
    @StateObject private var viewModel = LockboxesViewModel()
    @State private var showModal = false
    
    var body: some View {
        NavigationStack {
            if viewModel.boxes.isEmpty {
                EmptyScreenTemplate(
                    imageResource: .fadingPlus,
                    title: String(localized: "manage_devices"),
                    subtitle: String(localized: "here_will_be_devices_you_add"),
                    buttonText: String(localized: "add_device"),
                    action: { showModal = true }
                )
                .frame(maxWidth: .infinity, maxHeight: .infinity, alignment: .center)
                .background(.grey950)
                .sheet(isPresented: $showModal) {
                   
                }
            } else {
                ScrollView {
                    VStack(alignment: .center) {
                        ForEach(viewModel.boxes, id: \.box.macAddress) { boxWithStatus in
                            NavigationLink(value: boxWithStatus) {
                                Lockbox(box: boxWithStatus)
                            }
                            LockboxHorizontalDivider()
                        }
                        AddRow(title: String(localized: "add_device"))
                            .onTapGesture { showModal = true }
                            .padding(.top, 10)
                    }
                }
                .scrollContentBackground(.hidden)
                .background(.grey950)
                .navigationTitle(Text(String(localized: "main")))
                .navigationBarHidden(true)
                .navigationDestination(for: BoxWithStatus.self) { box in
                    DetailedLockboxScreen(mac: box.box.macAddress)
                }
                .sheet(isPresented: $showModal) {
                   
                }
            }
        }
    }
}

private struct Lockbox: View {
    private let box: BoxWithStatus
    
    init(box: BoxWithStatus) {
        self.box = box
    }
    
    var body: some View {
        HStack(alignment: .center, spacing: 12) {
            Image(.lock)
                .resizable()
                .scaledToFit()
                .frame(width: 30, height: 30)
                .foregroundStyle(.grey500)
            
            
            VStack(alignment: .leading, spacing: 2) {
                Text(box.box.alias)
                    .headlineLarge()
                    .lineLimit(1, reservesSpace: false)
                    .foregroundStyle(.white)
                
                Text(box.box.address)
                    .footnote()
                    .foregroundStyle(.grey500)
                    .lineLimit(2, reservesSpace: false)
            }.frame(maxWidth: .infinity, alignment: .leading)
            
            if box.status.batteryLevel != nil {
                Image(.alert)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
                    .foregroundStyle(.red500)
            }
            
            Image(.chevronRight)
                .resizable()
                .scaledToFit()
                .frame(width: 24, height: 24)
                .foregroundStyle(.grey500)
        }
        .padding(.horizontal, 16)
    }
}

#Preview {
    LockboxesScreen()
}

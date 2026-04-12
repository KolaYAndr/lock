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
    var body: some View {
        NavigationStack {
            List(viewModel.boxes, id: \.box.macAddress) { boxWithStatus in
                ZStack {
                    NavigationLink(value: boxWithStatus.box.macAddress) {
                        Lockbox(box: boxWithStatus.box)
                    }
                }
                .listRowBackground(Color.grey950)
                .listRowSeparatorTint(Color.grey500, edges: .bottom)
            }
            .scrollContentBackground(.hidden)
            .background(.grey950)
            .navigationTitle(Text(String(localized: "main")))
            .navigationBarHidden(true)
            .navigationDestination(for: String.self) { macAddress in
                Text("Details \(macAddress)")
            }
        }
    }
    
    private struct Lockbox: View {
        private let box: Box
        
        init(box: Box) {
            self.box = box
        }
        
        var body: some View {
            HStack(alignment: .center, spacing: 12) {
                Image(.lock)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
                    .foregroundStyle(.grey500)
                
                
                VStack(alignment: .leading, spacing: 2) {
                    Text(box.alias)
                        .headlineLarge()
                        .lineLimit(1, reservesSpace: false)
                        .foregroundStyle(.white)
                    
                    Text(box.address)
                        .footnote()
                        .foregroundStyle(.grey500)
                        .lineLimit(2, reservesSpace: false)
                }.frame(maxWidth: .infinity, alignment: .leading)
            }
            .padding(.horizontal, 16)
        }
    }
}

#Preview {
    LockboxesScreen()
}

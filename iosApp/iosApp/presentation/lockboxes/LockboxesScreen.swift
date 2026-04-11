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
        List(viewModel.boxes, id: \.box.macAddress) { boxWithStatus in
            ZStack {
                NavigationLink(value: boxWithStatus.box.macAddress) {
                    EmptyView()
                }.opacity(0)
                
                Lockbox(box: boxWithStatus.box)
            }.listRowBackground(Color.grey950)
                .listRowSeparatorTint(Color.grey500, edges: .bottom)
        }
        .scrollContentBackground(.hidden)
        .background(.grey950)
        .navigationDestination(for: String.self) { macAddress in
            Text("Details \(macAddress)")
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
                
                VStack(alignment: .leading, spacing: 2) {
                    Text(box.alias)
                        .headlineLarge()
                        .foregroundStyle(.white)
                    
                    Text(box.address)
                        .footnote()
                        .foregroundStyle(.grey500)
                }.frame(maxWidth: .infinity, alignment: .leading)
                
                
                Image(.chevronRight)
                    .resizable()
                    .scaledToFit()
                    .frame(width: 24, height: 24)
            }
            .padding(.horizontal, 16)
        }
    }
}

//
//  AccountScreen.swift
//  iosApp
//
//  Created by Николай on 15.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI
import PhotosUI

internal struct AccountScreen: View {
    private let viewModel = AccountViewModel()
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var imageData: Data? = nil
    @State private var showAlert = false
    
    var body: some View {
        ScrollView {
            VStack {
                VStack {
                    if let data = imageData,
                       let uiImage = UIImage(data: data) {
                        Image(uiImage: uiImage)
                            .resizable()
                            .scaledToFit()
                            .frame(width: 64, height: 64)
                            .clipShape(.circle)
                            .padding(.top, 24)
                    } else {
                        NetworkImage(url: viewModel.imageLink)
                            .padding(.top, 24)
                    }
                    
                    PhotosPicker(
                        String(localized: "load_account_photo"),
                        selection: $selectedItem,
                        matching: .images
                    )
                    .buttonStyle(.plain)
                    .foregroundStyle(.grey500)
                    .padding(.top, 12)
                    
                    Button {
                        showAlert = true
                    } label: {
                        Text(String(localized: "delete_account"))
                            .foregroundStyle(.red600)
                    }
                    .buttonStyle(.plain)
                    .padding(.top, 24)
                }
                .onChange(of: selectedItem) { _, newValue in
                    Task {
                        guard let data = try await newValue?.loadTransferable(type: Data.self) else {
                            return
                        }
                        imageData = data
                        viewModel.sendImage(data: data)
                    }
                }
                .onAppear {
                    viewModel.fetchInfo()
                }
                .frame(maxHeight: .infinity, alignment: .top)
            }
            .alert(isPresented: $showAlert) {
                Alert(
                    title: Text(String(localized: "delete_account_question")),
                    message: Text(String(localized: "not_cancellable_delete_account")),
                    primaryButton: .default(
                        Text(String(localized: "cancel")),
                        action: { showAlert = false }
                    ),
                    secondaryButton: .destructive(
                        Text(String(localized: "delete")),
                        action: { viewModel.deleteAccount() }
                    )
                )
            }
        }
        .navigationTitle(Text(String(localized: "account")))
        .refreshable {
            await viewModel.refreshAccountInfo()
        }
    }
}

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
    @StateObject private var viewModel = AccountViewModel()
    @State private var selectedItem: PhotosPickerItem? = nil
    @State private var imageData: Data? = nil
    @State private var showAlert = false
    @State private var currentFullName: String = ""
    @Binding var path: NavigationPath
    
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
                    
                    LockboxCredentialsInput(
                        text: $currentFullName,
                        placeholderText: String(localized: "input_full_name"),
                        canSave: currentFullName != viewModel.fullName,
                        onCancel: { currentFullName = viewModel.fullName },
                        onFinish: {
                            viewModel.fullName = currentFullName
                            viewModel.updatePersonalName()
                        }
                    )
                    .padding(.top, 24)
                    .padding(.horizontal, 16)
                    
                    Button {
                        showAlert = true
                    } label: {
                        Text(String(localized: "delete_account"))
                            .frame(maxWidth: .infinity, alignment: .center)
                    }
                    .buttonStyle(.redGreyBackground)
                    .padding(.horizontal, 16)
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
                .onChange(of: viewModel.fullName) { _, newValue in
                    currentFullName = newValue
                }
                .onAppear {
                    currentFullName = viewModel.fullName
                    viewModel.fetchInfo()
                }
                .frame(maxHeight: .infinity, alignment: .top)
            }
            .alert(isPresented: $showAlert) {
                Alert(
                    title: Text(String(localized: "delete_account_question")),
                    message: Text(String(localized: "not_cancellable_delete_account")),
                    primaryButton: .cancel(
                        Text(String(localized: "cancel")),
                        action: { showAlert = false }
                    ),
                    secondaryButton: .destructive(
                        Text(String(localized: "delete")),
                        action: {
                            viewModel.deleteAccount {
                                path.append(SettingsRoute.warning)
                            }
                        }
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


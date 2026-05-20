//
//  SettingsScreen.swift
//  iosApp
//
//  Created by Николай on 12.04.2026.
//  Copyright © 2026 Lockbox. All rights reserved.
//

import SwiftUI

internal struct SettingsScreen: View {
    @State private var path = NavigationPath()
    @StateObject private var viewModel = SettingsViewModel()
    @State private var showHelpModal: Bool = false
    @State private var showAlert: Bool = false
    
    var body: some View {
        NavigationStack(path: $path) {
            ZStack {
                Color.grey950.ignoresSafeArea()
                
                ScrollView {
                    VStack {
                        NetworkImage(url: viewModel.imageLink)
                            .padding(.top, 12)
                        
                        if !viewModel.fullName.isEmpty {
                            Text(viewModel.fullName)
                                .titleMedium()
                                .foregroundStyle(.grey50)
                                .frame(maxWidth: .infinity, alignment: .center)
                                .multilineTextAlignment(.center)
                                .padding(.top, 12)
                                .transition(.move(edge: .top).combined(with: .opacity))
                        }
                        
                        if !viewModel.email.isEmpty {
                            Text(viewModel.email)
                                .subHeadlineMedium()
                                .foregroundStyle(.grey500)
                                .frame(maxWidth: .infinity, alignment: .center)
                                .multilineTextAlignment(.center)
                                .padding(.top, 2)
                                .transition(.move(edge: .top).combined(with: .opacity))
                        }
                        
                        LockboxGroup {
                            NavigationLink(value: SettingsRoute.account) {
                                ImageTextArrow(text: String(localized: "account"), imageRes: .user)
                                    .padding(.top, 4)
                                    .padding(.horizontal, 16)
                                    .frame(maxWidth: .infinity, alignment: .center)
                            }
                            
                            LockboxHorizontalDivider()
                            
                            NavigationLink(value: SettingsRoute.security) {
                                ImageTextArrow(text: String(localized: "security"), imageRes: .shield)
                                    .padding(.bottom, 4)
                                    .padding(.horizontal, 16)
                                    .frame(maxWidth: .infinity, alignment: .center)
                            }
                        }
                        .padding(.top, 24)
                        
                        LockboxGroup {
                            NavigationLink(value: SettingsRoute.aboutApp) {
                                ImageTextArrow(text: String(localized: "about_app"), imageRes: .alert)
                                    .padding(.top, 4)
                                    .padding(.horizontal, 16)
                                    .frame(maxWidth: .infinity, alignment: .center)
                            }
                            
                            LockboxHorizontalDivider()
                            
                            ImageTextArrow(text: String(localized: "help"), imageRes: .headset)
                                .padding(.bottom, 4)
                                .padding(.horizontal, 16)
                                .frame(maxWidth: .infinity, alignment: .center)
                                .onTapGesture {
                                    showHelpModal = true
                                }
                        }
                        .padding(.top, 24)
                        
                        Button {
                            showAlert = true
                        } label:  {
                            HStack(alignment: .center, spacing: 12) {
                                Image(.logOut)
                                    .resizable()
                                    .scaledToFit()
                                    .frame(width: 24, height: 24)
                                
                                Text(String(localized: "log_out"))
                                    .headlineLarge()
                            }
                        }
                        .buttonStyle(.darkRed)
                        .frame(maxWidth: .infinity, alignment: .center)
                        .padding(.top, 24)
                    }
                    .animation(.easeInOut(duration: 0.25), value: viewModel.fullName.isEmpty)
                    .animation(.easeInOut(duration: 0.25), value: viewModel.email.isEmpty)
                    .onAppear {
                        viewModel.fetchInfo()
                    }
                    .sheet(isPresented: $showHelpModal) {
                        HelpModalSheet()
                    }
                    .alert(isPresented: $showAlert) {
                        Alert(
                            title: Text(String(localized: "log_out_question")),
                            primaryButton: .cancel(
                                Text(String(localized: "cancel")),
                                action: { showAlert = false }
                            ),
                            secondaryButton: .destructive(
                                Text(String(localized: "log_out")),
                                action: { viewModel.logOut() }
                            )
                        )
                    }
                    .padding(.horizontal, 16)
                    .frame(maxHeight: .infinity, alignment: .top)
                }
                .refreshable {
                    await viewModel.refreshAccountInfo()
                }
                .navigationTitle(Text(String(localized: "settings")))
                .navigationBarHidden(true)
                .navigationDestination(for: SettingsRoute.self) { route in
                    switch route {
                    case .aboutApp:
                        AboutAppScreen()
                    case .account:
                        AccountScreen(path: $path)
                    case .warning:
                        WarningScreen()
                    case .security:
                        SecurityScreen()
                    case .activeSessions:
                        ActiveSessionsScreen()
                    }
                }
            }
        }
    }
}

#Preview {
    SettingsScreen()
}

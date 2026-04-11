//
//  AuthenticatedNavigation.swift
//  iosApp
//
//  Created by Николай on 10.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct AuthenticatedNavigation : View {
    private let logOut: () -> Void
    
    init(logOut: @escaping () -> Void) {
        self.logOut = logOut
    }
    
    var body: some View {
        TabView {
            BoxesNavigation()
                .tabItem {
                    Label(String(localized: "main"), image: .house)
                }
            
            Text(String(localized: "contracts"))
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .foregroundStyle(.grey100)
                .background(Color.grey950)
                .ignoresSafeArea()
                .tabItem {
                    Label(String(localized: "contracts"), image: .contract)
                }
            
            Text(String(localized: "settings"))
                .onTapGesture {
                    logOut()
                }
                .frame(maxWidth: .infinity, maxHeight: .infinity)
                .foregroundStyle(.grey100)
                .background(Color.grey950)
                .ignoresSafeArea()
                .tabItem {
                    Label(String(localized: "settings"), image: .settings)
                }
        }
        .toolbarBackground(.visible, for: .tabBar)
        .toolbarBackground(.grey1000, for: .tabBar)
        .tint(.green500)
    }
}

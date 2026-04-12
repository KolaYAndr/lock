//
//  NetworkImage.swift
//  iosApp
//
//  Created by Николай on 12.04.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

internal struct NetworkImage: View {
    private let url: URL?
    
    init(url: String) {
        self.url = URL(string: url)
    }
    
    var body: some View {
        AsyncImage(url: url) { phase in
            switch phase {
            case .empty:
                Image(.user)
                    .resizable()
                    .scaledToFit()
            case .success(let image):
                image
                    .resizable()
                    .scaledToFit()
            case .failure:
                Image(.user)
                    .resizable()
                    .scaledToFit()
            @unknown default:
                Image(.user)
                    .resizable()
                    .scaledToFit()
            }
        }
        .frame(width: 64, height: 64)
        .foregroundStyle(.grey500)
        .background(.grey800)
        .clipShape(.circle)
        .overlay(
            Circle().stroke(.grey700, lineWidth: 0.5)
        )
    }
}

#Preview {
    NetworkImage(url: "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRv7NB3-K7GJ2X2XFRz8S7cM4g_W7tCyhDt9Q&s")
}


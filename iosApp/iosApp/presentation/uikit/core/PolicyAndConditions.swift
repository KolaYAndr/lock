//
//  PolicyAndConditions.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct PolicyAndConditions: View {
    private let privacyPolicyUrl = URL(string: "https://api.lockboxes.ru:9000/policies/privacy_policy_black.pdf")!
    private let userAgreementUrl = URL(string: "https://api.lockboxes.ru:9000/policies/user_agreement_black.pdf")!
    
    var body: some View {
        
        Text(makeAttributedText())
            .footnote()
            .foregroundStyle(.grey700)
            .fixedSize(horizontal: false, vertical: true)
            .lineLimit(nil)
            .multilineTextAlignment(.center)
    }
    
    private func makeAttributedText() -> AttributedString {
        // 1. Готовим чистые строки для названий
        let serviceTitle = String(localized: "with_service_conditions")
        let privacyTitle = String(localized: "with_privacy_policy")
        
        // 2. Создаем ресурс, ГДЕ КЛЮЧ — это ровно "by_pressing_you_agree"
        // Аргументы передаются ВНУТРИ defaultValue в нужном порядке
        let resource = LocalizedStringResource(
            "by_pressing_you_agree",
            defaultValue: "\(serviceTitle) \(userAgreementUrl.absoluteString) \(privacyTitle) \(privacyPolicyUrl.absoluteString)"
        )
        
        // 3. Создаем AttributedString. SwiftUI сам распарсит Markdown из каталога ([текст](ссылка))
        return AttributedString(localized: resource)
    }
}

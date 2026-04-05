//
//  PolicyAndConditions.swift
//  iosApp
//
//  Created by Николай on 24.03.2026.
//  Copyright © 2026 LockBox. All rights reserved.
//

import SwiftUI

struct PolicyAndConditions: View {
    private let privacyPolicyUrl = URL(string: "https://legal.s3.lockboxes.ru/privacy-policy-black.pdf")!
    private let userAgreementUrl = URL(string: "https://legal.s3.lockboxes.ru/terms-of-service-black.pdf")!
    
    var body: some View {
        Text(makeAttributedText())
            .footnote()
            .foregroundStyle(.grey700)
            .fixedSize(horizontal: false, vertical: true)
            .lineLimit(nil)
            .multilineTextAlignment(.center)
    }
    
    private func makeAttributedText() -> AttributedString {
        let serviceTitle = String(localized: "with_service_conditions")
        let privacyTitle = String(localized: "with_privacy_policy")
        
        let resource = LocalizedStringResource(
            "by_pressing_you_agree",
            defaultValue: "\(serviceTitle) \(userAgreementUrl.absoluteString) \(privacyTitle) \(privacyPolicyUrl.absoluteString)"
        )
        
        return AttributedString(localized: resource)
    }
}

import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        SharedAppModulesKt.doInitSharedKoin()
    }

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

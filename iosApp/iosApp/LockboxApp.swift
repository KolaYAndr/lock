import SwiftUI
import Shared

@main
struct LockboxApp: App {
    init() {
        KoinHelperKt.doInitKoin()
    }
    
    var body: some Scene {
        WindowGroup {
            LoginScreen()
        }
    }
}

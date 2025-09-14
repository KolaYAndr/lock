import SwiftUI
import Shared

@main
struct iOSApp: App {
    init() {
        DiHelper.shared.doInitSharedKoin()
    }
    private var lockboxActivityViewModel: LockboxActivityViewModel = get()

    var body: some Scene {
        WindowGroup {
            ContentView()
        }
    }
}

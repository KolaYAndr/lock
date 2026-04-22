import SwiftUI
import Shared

@main
struct LockboxApp: App {
    init() {
        KoinHelperKt.doInitKoin()
    }
    
    @StateObject private var viewModel = LockboxAppViewModel()
    
    var body: some Scene {
        WindowGroup {
//            switch viewModel.loginState {
//            case .loggedIn: AuthenticatedNavigation(logOut: viewModel.logOut).preferredColorScheme(.dark)
//            case .loading: LockboxCircularProgressIndicator().frame(alignment: .center).preferredColorScheme(.dark)
//            case .notLoggedIn: LoginScreen().preferredColorScheme(.dark)
//            }
            AuthenticatedNavigation(logOut: viewModel.logOut).preferredColorScheme(.dark)
        }
    }
}

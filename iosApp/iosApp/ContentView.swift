import SwiftUI
import Shared

struct ContentView: View {
    var body: some View {
        VStack {
            Text("SwiftUI")

            HStack {
                Text("From Android Studio")

                Text("It's lit").padding()
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

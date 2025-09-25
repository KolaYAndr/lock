import SwiftUI

struct ContentView: View {
    var body: some View {
        VStack {
            Text("Large title")
                .font(.titleLarge)
                .lineSpacing(.spacingLarge)
                .foregroundColor(.red100)
            
            Text("Footnote")
                .font(.footnote)
                .lineSpacing(.spacingSmall)
                .foregroundColor(.grey900)
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

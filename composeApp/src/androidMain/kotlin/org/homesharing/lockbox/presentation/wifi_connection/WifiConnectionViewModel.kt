package org.homesharing.lockbox.presentation.wifi_connection

import android.annotation.SuppressLint
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiInfo
import android.net.wifi.WifiManager
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.koin.core.component.getScopeName

private const val LOCKBOX_WIFI_NETWORK_NAME = "LockBox-Network"

class WifiConnectionViewModel(
    private val connectivityManager: ConnectivityManager,
) : ViewModel() {
    private val _uiState: MutableStateFlow<WifiConnectionUiState> =
        MutableStateFlow(WifiConnectionUiState())
    val uiState: StateFlow<WifiConnectionUiState> get() = _uiState.asStateFlow()

//    init {
//        observeConnection()
//    }

//    private fun observeConnection() {
//        val request = NetworkRequest.Builder()
//            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
//            .build()
//
//        val networkCallback = object : ConnectivityManager.NetworkCallback() {
//            override fun onCapabilitiesChanged(
//                network: Network,
//                networkCapabilities: NetworkCapabilities
//            ) {
//                super.onCapabilitiesChanged(network, networkCapabilities)
//                val networkName = (networkCapabilities.transportInfo as WifiInfo).ssid
//                _uiState.update { WifiConnectionUiState(connectedNetworkName = networkName) }
//            }
//        }
//
//        connectivityManager.registerNetworkCallback(request, networkCallback)
//    }
}

data class WifiConnectionUiState(
    val connectedNetworkName: String = "",
    val connectedToLockBoxNetwork: Boolean = connectedNetworkName == LOCKBOX_WIFI_NETWORK_NAME,
)

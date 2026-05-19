//package org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal
//
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.Spacer
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.foundation.rememberScrollState
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.foundation.verticalScroll
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.remember
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.focus.FocusRequester
//import androidx.compose.ui.focus.focusRequester
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.unit.dp
//import androidx.lifecycle.compose.collectAsStateWithLifecycle
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.composables.TitledTopWithCloseAndBackButtons
//import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxHorizontalDivider
//import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
//import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxModal
//import org.homesharing.lockbox.presentation.uikit.composables.core.inputs.LockBoxPropertyInput
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//import org.koin.androidx.compose.koinViewModel
//
//@Composable
//internal fun AddVirtualDeviceModal(
//    modifier: Modifier,
//    onDismiss: () -> Unit,
//    onFinish: () -> Unit,
//    addVirtualDeviceViewModel: AddVirtualDeviceModalViewModel = koinViewModel(),
//) {
//    val addVirtualDeviceUiState by addVirtualDeviceViewModel.addVirtualDeviceUiState.collectAsStateWithLifecycle()
//    AddVirtualDeviceModal(
//        modifier = modifier,
//        onDismiss = onDismiss,
//        deviceName = addVirtualDeviceUiState.deviceName,
//        onChangeDeviceName = addVirtualDeviceViewModel::onChangeDeviceName,
//        deviceAddress = addVirtualDeviceUiState.macAddress,
//        onChangeMacAddress = addVirtualDeviceViewModel::onChangeMacAddress,
//        onChangeDeviceAddress = addVirtualDeviceViewModel::onChangeDeviceAddress,
//        onCreateVirtualDevice = {
//            addVirtualDeviceViewModel.onCreateVirtualDevice()
//            onFinish()
//            onDismiss()
//        }
//    )
//}
//
//@Composable
//private fun AddVirtualDeviceModal(
//    onDismiss: () -> Unit,
//    deviceName: String,
//    onChangeDeviceName: (String) -> Unit,
//    deviceAddress: String,
//    onChangeMacAddress: (String) -> Unit,
//    onChangeDeviceAddress: (String) -> Unit,
//    onCreateVirtualDevice: () -> Unit,
//    modifier: Modifier = Modifier,
//) {
//    val nameFocusRequester = remember { FocusRequester() }
//    val macAddressFocusRequester = remember { FocusRequester() }
//    val addressFocusRequester = remember { FocusRequester() }
//    LaunchedEffect(Unit) { nameFocusRequester.requestFocus() }
//
//    LockboxModal(
//        onDismiss = onDismiss,
//        modifier = modifier
//    ) {
//        TitledTopWithCloseAndBackButtons(
//            title = stringResource(R.string.device_connection),
//            onDismiss = onDismiss,
//            modifier = Modifier.fillMaxWidth()
//        )
//
//        LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())
//
//        Column(
//            modifier = modifier.verticalScroll(rememberScrollState())
//        ) {
//            Column(
//                modifier = Modifier.padding(
//                    start = 32.dp,
//                    end = 32.dp,
//                    bottom = 32.dp,
//                    top = 48.dp
//                ),
//                horizontalAlignment = Alignment.CenterHorizontally,
//                verticalArrangement = Arrangement.Center
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.settings),
//                    contentDescription = null,
//                    tint = LockBoxTheme.colors.grey500,
//                    modifier = Modifier.size(48.dp),
//                )
//
//                Text(
//                    text = stringResource(R.string.set_up_a_device),
//                    style = LockBoxTheme.typography.titleMedium,
//                    color = LockBoxTheme.colors.grey100,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(top = 12.dp)
//                )
//
//                Text(
//                    text = stringResource(R.string.set_name_and_address_to_finish),
//                    style = LockBoxTheme.typography.bodyMedium,
//                    color = LockBoxTheme.colors.grey500,
//                    textAlign = TextAlign.Center,
//                    modifier = Modifier.padding(top = 8.dp)
//                )
//            }
//
//            Column(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
//            ) {
//                Text(
//                    text = stringResource(R.string.device_name),
//                    color = LockBoxTheme.colors.grey100,
//                    style = LockBoxTheme.typography.subHeadlineLarge,
//                )
//
//                LockBoxPropertyInput(
//                    value = deviceName,
//                    placeholderText = stringResource(R.string.device_name_placeholder),
//                    onValueChange = onChangeDeviceName,
//                    keyboardActions = KeyboardActions(
//                        onAny = { addressFocusRequester.requestFocus() }
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        showKeyboardOnFocus = true,
//                        imeAction = ImeAction.Next,
//                    ),
//                    modifier = Modifier
//                        .focusRequester(nameFocusRequester)
//                        .fillMaxWidth()
//                )
//            }
//
//            Column(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
//            ) {
//                Text(
//                    text = stringResource(R.string.device_address),
//                    color = LockBoxTheme.colors.grey100,
//                    style = LockBoxTheme.typography.subHeadlineLarge,
//                )
//
//                LockBoxPropertyInput(
//                    value = deviceAddress,
//                    placeholderText = stringResource(R.string.device_address_placeholder),
//                    onValueChange = onChangeDeviceAddress,
//                    keyboardActions = KeyboardActions(
//                        onAny = { macAddressFocusRequester.requestFocus() }
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        showKeyboardOnFocus = true,
//                        imeAction = ImeAction.Done,
//                    ),
//                    modifier = Modifier
//                        .focusRequester(addressFocusRequester)
//                        .fillMaxWidth()
//                )
//            }
//
//            Column(
//                modifier = modifier
//                    .fillMaxWidth()
//                    .padding(horizontal = 16.dp, vertical = 12.dp),
//                horizontalAlignment = Alignment.Start,
//                verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically)
//            ) {
//                Text(
//                    text = stringResource(R.string.device_mac_address),
//                    color = LockBoxTheme.colors.grey100,
//                    style = LockBoxTheme.typography.subHeadlineLarge,
//                )
//
//                LockBoxPropertyInput(
//                    value = deviceAddress,
//                    placeholderText = stringResource(R.string.device_mac_address_placeholder),
//                    onValueChange = onChangeMacAddress,
//                    keyboardActions = KeyboardActions(
//                        onAny = {
//                            onCreateVirtualDevice()
//                            onDismiss()
//                        }
//                    ),
//                    keyboardOptions = KeyboardOptions(
//                        keyboardType = KeyboardType.Text,
//                        showKeyboardOnFocus = true,
//                        imeAction = ImeAction.Done,
//                    ),
//                    modifier = Modifier
//                        .focusRequester(addressFocusRequester)
//                        .fillMaxWidth()
//                )
//            }
//
//            Spacer(modifier = Modifier.weight(1f))
//
//            LockboxButton(
//                onClick = {
//                    onCreateVirtualDevice()
//                    onDismiss()
//                },
//                text = stringResource(R.string.finish_setting_up),
//                paddingValues = PaddingValues(14.dp),
//                modifier = Modifier
//                    .fillMaxSize()
//                    .padding(vertical = 12.dp, horizontal = 16.dp)
//            )
//        }
//    }
//}

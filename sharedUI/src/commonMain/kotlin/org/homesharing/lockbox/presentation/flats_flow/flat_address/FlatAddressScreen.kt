package org.homesharing.lockbox.presentation.flats_flow.flat_address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.address
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.ic_close
import lockbox.sharedui.generated.resources.comment_placeholder
import lockbox.sharedui.generated.resources.commercial
import lockbox.sharedui.generated.resources.ic_commercial
import lockbox.sharedui.generated.resources.flat_type
import lockbox.sharedui.generated.resources.ic_house
import lockbox.sharedui.generated.resources.living
import lockbox.sharedui.generated.resources.name_example
import lockbox.sharedui.generated.resources.new_flat
import lockbox.sharedui.generated.resources.placeholder_building
import lockbox.sharedui.generated.resources.placeholder_city
import lockbox.sharedui.generated.resources.placeholder_entrance
import lockbox.sharedui.generated.resources.placeholder_flat_number
import lockbox.sharedui.generated.resources.placeholder_liter
import lockbox.sharedui.generated.resources.placeholder_name
import lockbox.sharedui.generated.resources.placeholder_number
import lockbox.sharedui.generated.resources.placeholder_street
import lockbox.sharedui.generated.resources.placeholder_structure
import lockbox.sharedui.generated.resources.proceed
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.composable.pickable.HomesharingPickableItem
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FlatAddressScreen(
    viewModel: FlatAddressViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
) {
    val flatAddress by viewModel.flatAddress.collectAsStateWithLifecycle()
    FlatAddressScreen(
        flatAddress = flatAddress,
        onIntent = viewModel::onIntent,
        onProceed = onProceed,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlatAddressScreen(
    flatAddress: FlatAddress,
    onIntent: (FlatAddressIntent) -> Unit,
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.new_flat),
                    style = HomesharingTheme.typography.bodyMedium,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = onNavBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(Res.string.address),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        val cityFocusRequester = remember { FocusRequester() }
        val streetFocusRequester = remember { FocusRequester() }
        val numberFocusRequester = remember { FocusRequester() }
        val entranceFocusRequester = remember { FocusRequester() }
        val flatNumberFocusRequester = remember { FocusRequester() }
        val buildingFocusRequester = remember { FocusRequester() }
        val structureFocusRequester = remember { FocusRequester() }
        val literFocusRequester = remember { FocusRequester() }
        val commentFocusRequester = remember { FocusRequester() }
        val nameFocusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            cityFocusRequester.requestFocus()
        }

        HomesharingInput(
            value = flatAddress.city,
            onValueChange = { onIntent(FlatAddressIntent.CityChanged(it)) },
            placeholderText = stringResource(Res.string.placeholder_city),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = { onIntent(FlatAddressIntent.CityChanged("")) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Words,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    streetFocusRequester.requestFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp)
                .focusRequester(cityFocusRequester)
        )

        HomesharingInput(
            value = flatAddress.street,
            onValueChange = { onIntent(FlatAddressIntent.StreetChanged(it)) },
            placeholderText = stringResource(Res.string.placeholder_street),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = { onIntent(FlatAddressIntent.StreetChanged("")) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    numberFocusRequester.requestFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp)
                .focusRequester(streetFocusRequester)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingInput(
                value = flatAddress.number,
                onValueChange = { onIntent(FlatAddressIntent.NumberChanged(it)) },
                placeholderText = stringResource(Res.string.placeholder_number),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = { onIntent(FlatAddressIntent.NumberChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        entranceFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(numberFocusRequester)
            )

            HomesharingInput(
                value = flatAddress.entrance,
                onValueChange = { onIntent(FlatAddressIntent.EntranceChanged(it)) },
                placeholderText = stringResource(Res.string.placeholder_entrance),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = { onIntent(FlatAddressIntent.EntranceChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        flatNumberFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(entranceFocusRequester)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingInput(
                value = flatAddress.flatNumber,
                onValueChange = { onIntent(FlatAddressIntent.FlatNumberChanged(it)) },
                placeholderText = stringResource(Res.string.placeholder_flat_number),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = { onIntent(FlatAddressIntent.FlatNumberChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        buildingFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(flatNumberFocusRequester)
            )

            HomesharingInput(
                value = flatAddress.building,
                onValueChange = { onIntent(FlatAddressIntent.BuildingChanged(it)) },
                placeholderText = stringResource(Res.string.placeholder_building),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = { onIntent(FlatAddressIntent.BuildingChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        structureFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(buildingFocusRequester)
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingInput(
                value = flatAddress.structure,
                onValueChange = { onIntent(FlatAddressIntent.StructureChanged(it)) },
                placeholderText = stringResource(Res.string.placeholder_structure),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = { onIntent(FlatAddressIntent.StructureChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        literFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(structureFocusRequester)
            )

            HomesharingInput(
                value = flatAddress.liter,
                onValueChange = { onIntent(FlatAddressIntent.LiterChanged(it)) },
                placeholderText = stringResource(Res.string.placeholder_liter),
                trailingIcon = painterResource(Res.drawable.ic_close),
                trailingIconAction = { onIntent(FlatAddressIntent.LiterChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        nameFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(literFocusRequester)
            )
        }

        HomesharingInput(
            value = flatAddress.name,
            onValueChange = { onIntent(FlatAddressIntent.NameChanged(it)) },
            placeholderText = stringResource(Res.string.placeholder_name),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = { onIntent(FlatAddressIntent.NameChanged("")) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    commentFocusRequester.requestFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp)
                .focusRequester(nameFocusRequester)
        )

        HomesharingInput(
            value = flatAddress.comment,
            singleLine = false,
            onValueChange = { onIntent(FlatAddressIntent.CommentChanged(it)) },
            placeholderText = stringResource(Res.string.comment_placeholder),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = { onIntent(FlatAddressIntent.CommentChanged("")) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp)
                .focusRequester(commentFocusRequester)
                .heightIn(min = 96.dp)
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 16.dp),
            text = stringResource(Res.string.name_example),
            style = HomesharingTheme.typography.bodySmall,
            color = HomesharingTheme.colors.black.copy(alpha = .5f)
        )

        Text(
            text = stringResource(Res.string.flat_type),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            HomesharingPickableItem(
                icon = painterResource(Res.drawable.ic_house),
                title = stringResource(Res.string.living),
                isSelected = flatAddress.flatType == FlatAddress.FlatType.Living,
                onClick = {
                    onIntent(FlatAddressIntent.FlatTypeChanged(FlatAddress.FlatType.Living))
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )

            HomesharingPickableItem(
                icon = painterResource(Res.drawable.ic_commercial),
                title = stringResource(Res.string.commercial),
                isSelected = flatAddress.flatType == FlatAddress.FlatType.Commercial,
                onClick = {
                    onIntent(FlatAddressIntent.FlatTypeChanged(FlatAddress.FlatType.Commercial))
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )
        }

        HomesharingButton(
            title = stringResource(Res.string.proceed),
            onClick = onProceed,
            enabled = flatAddress.city.isNotBlank() &&
                    flatAddress.street.isNotBlank() &&
                    flatAddress.number.isNotBlank() &&
                    flatAddress.name.isNotBlank() &&
                    flatAddress.flatType != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 24.dp)
                .padding(horizontal = 11.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewFlatAddressScreen() {
    HomesharingTheme {
        FlatAddressScreen(
            flatAddress = FlatAddress.Initial,
            onIntent = {},
            onProceed = {},
            onNavBack = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}
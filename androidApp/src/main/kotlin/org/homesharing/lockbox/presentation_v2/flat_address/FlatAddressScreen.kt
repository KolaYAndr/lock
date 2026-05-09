package org.homesharing.lockbox.presentation_v2.flat_address

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation_v2.uikit.composable.pick.HomesharingPickableItem
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun FlatAddressScreen(
    viewModel: FlatAddressViewModel = koinViewModel()
) {
    val flatAddress by viewModel.flatAddress.collectAsStateWithLifecycle()
    FlatAddressScreen(
        flatAddress = flatAddress,
        onIntent = viewModel::onIntent,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlatAddressScreen(
    flatAddress: FlatAddress,
    onIntent: (FlatAddressIntent) -> Unit,
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
                    text = stringResource(R.string.new_flat),
                    style = HomesharingTheme.typography.bodyLarge,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(R.string.address),
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
            placeholderText = stringResource(R.string.placeholder_city),
            trailingIcon = painterResource(R.drawable.close),
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
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .focusRequester(cityFocusRequester)
        )

        HomesharingInput(
            value = flatAddress.street,
            onValueChange = { onIntent(FlatAddressIntent.StreetChanged(it)) },
            placeholderText = stringResource(R.string.placeholder_street),
            trailingIcon = painterResource(R.drawable.close),
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
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .focusRequester(streetFocusRequester)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingInput(
                value = flatAddress.number,
                onValueChange = { onIntent(FlatAddressIntent.NumberChanged(it)) },
                placeholderText = stringResource(R.string.placeholder_number),
                trailingIcon = painterResource(R.drawable.close),
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
                placeholderText = stringResource(R.string.placeholder_entrance),
                trailingIcon = painterResource(R.drawable.close),
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
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingInput(
                value = flatAddress.flatNumber,
                onValueChange = { onIntent(FlatAddressIntent.FlatNumberChanged(it)) },
                placeholderText = stringResource(R.string.placeholder_flat_number),
                trailingIcon = painterResource(R.drawable.close),
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
                placeholderText = stringResource(R.string.placeholder_building),
                trailingIcon = painterResource(R.drawable.close),
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
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingInput(
                value = flatAddress.structure,
                onValueChange = { onIntent(FlatAddressIntent.StructureChanged(it)) },
                placeholderText = stringResource(R.string.placeholder_structure),
                trailingIcon = painterResource(R.drawable.close),
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
                placeholderText = stringResource(R.string.placeholder_liter),
                trailingIcon = painterResource(R.drawable.close),
                trailingIconAction = { onIntent(FlatAddressIntent.LiterChanged("")) },
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Text,
                    capitalization = KeyboardCapitalization.Sentences,
                    imeAction = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = {
                        commentFocusRequester.requestFocus()
                    }
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(literFocusRequester)
            )
        }

        HomesharingInput(
            value = flatAddress.comment,
            onValueChange = { onIntent(FlatAddressIntent.CommentChanged(it)) },
            placeholderText = stringResource(R.string.placeholder_comment),
            trailingIcon = painterResource(R.drawable.close),
            trailingIconAction = { onIntent(FlatAddressIntent.CommentChanged("")) },
            keyboardOptions = KeyboardOptions(
                capitalization = KeyboardCapitalization.Sentences,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Next
            ),
            keyboardActions = KeyboardActions(
                onNext = {
                    nameFocusRequester.requestFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .focusRequester(commentFocusRequester)
        )

        HomesharingInput(
            value = flatAddress.name,
            onValueChange = { onIntent(FlatAddressIntent.NameChanged(it)) },
            placeholderText = stringResource(R.string.placeholder_name),
            trailingIcon = painterResource(R.drawable.close),
            trailingIconAction = { onIntent(FlatAddressIntent.NameChanged("")) },
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
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .focusRequester(nameFocusRequester)
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 16.dp),
            text = stringResource(R.string.name_example),
            style = HomesharingTheme.typography.footnote,
            color = HomesharingTheme.colors.black.copy(alpha = .5f)
        )

        Text(
            text = stringResource(R.string.flat_type),
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
                icon = painterResource(R.drawable.house),
                title = stringResource(R.string.living),
                isSelected = flatAddress.flatType == FlatAddress.FlatType.Living,
                onClick = {
                    onIntent(FlatAddressIntent.FlatTypeChanged(FlatAddress.FlatType.Living))
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )

            HomesharingPickableItem(
                icon = painterResource(R.drawable.commercial),
                title = stringResource(R.string.commercial),
                isSelected = flatAddress.flatType == FlatAddress.FlatType.Commercial,
                onClick = {
                    onIntent(FlatAddressIntent.FlatTypeChanged(FlatAddress.FlatType.Commercial))
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )
        }

        HomesharingButton(
            title = stringResource(R.string.proceed),
            onClick = {},
            enabled = flatAddress.city.isNotBlank() &&
                    flatAddress.street.isNotBlank() &&
                    flatAddress.number.isNotBlank() &&
                    flatAddress.name.isNotBlank() &&
                    flatAddress.flatType != null,
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp, top = 24.dp)
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
        )
    }
}
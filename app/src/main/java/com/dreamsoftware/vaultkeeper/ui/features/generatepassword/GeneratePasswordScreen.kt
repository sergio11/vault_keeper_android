package com.dreamsoftware.vaultkeeper.ui.features.generatepassword

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ClipboardManager
import androidx.compose.ui.platform.LocalClipboardManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.brownie.component.screen.BrownieScreen

@Composable
fun GeneratePasswordScreen(
    viewModel: GenerateViewModel = hiltViewModel()
) {
    val clipboardManager: ClipboardManager = LocalClipboardManager.current
    BrownieScreen(
        viewModel = viewModel,
        onInitialUiState = { GenerateUiState() },
        onSideEffect = {

        },
        onInit = {
            generateInitialPassword()
        }
    ) { uiState ->
        GeneratePasswordScreenContent(
            uiState = uiState,
            clipboardManager = clipboardManager,
            actionListener = viewModel
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GenerateScreenPreview() {
    GeneratePasswordScreen()
}

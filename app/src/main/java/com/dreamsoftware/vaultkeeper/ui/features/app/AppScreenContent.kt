package com.dreamsoftware.vaultkeeper.ui.features.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController
import com.dreamsoftware.vaultkeeper.ui.navigation.graph.RootNavigationGraph

@Composable
fun AppScreenContent(
    uiState: AppUiState
) {
    RootNavigationGraph(navController = rememberNavController())
}
package com.dreamsoftware.vaultkeeper.ui.features.main

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.component.BottomNavBarItem
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BrownieViewModel<MainUiState, MainSideEffects>() {

    override fun onGetDefaultState(): MainUiState = MainUiState(mainDestinationList = listOf(
        BottomNavBarItem(
            route = Screens.Main.Home.Info.route,
            icon = R.drawable.icon_home,
            titleRes = R.string.home
        ),
        BottomNavBarItem(
            route = Screens.Main.Home.Generate.route,
            icon = R.drawable.icon_key,
            titleRes = R.string.generate
        ),
        BottomNavBarItem(
            route = Screens.Main.Home.Settings.route,
            icon = R.drawable.icon_settings,
            titleRes = R.string.settings
        )
    ))

    fun onBottomItemsVisibilityChanged(hideBottomItems: Boolean) {
        viewModelScope.launch {
            updateState {
                it.copy(shouldShowBottomNav = !hideBottomItems)
            }
        }
    }
}

data class MainUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val shouldShowBottomNav: Boolean = false,
    val hasSession: Boolean = true,
    val mainDestinationList: List<BottomNavBarItem> = emptyList()
): UiState<MainUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): MainUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface MainSideEffects: SideEffect
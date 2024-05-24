package com.dreamsoftware.lockbuddy.ui.features.main

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.lockbuddy.ui.features.main.model.BottomNavBarItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(): BrownieViewModel<MainUiState, MainSideEffects>() {
    override fun onGetDefaultState(): MainUiState = MainUiState(mainDestinationList = listOf(
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
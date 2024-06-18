package com.dreamsoftware.vaultkeeper.ui.features.main

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.brownie.component.BottomNavBarItem
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.usecase.LockAccountUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyUserAccountStatusUseCase
import com.dreamsoftware.vaultkeeper.ui.navigation.Screens
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val verifyUserAccountStatusUseCase: VerifyUserAccountStatusUseCase,
    private val lockAccountUseCase: LockAccountUseCase
): BrownieViewModel<MainUiState, MainSideEffects>() {

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

    fun onVerifyUserAccountStatus() {
        executeUseCase(
            useCase = verifyUserAccountStatusUseCase,
            onSuccess = ::onVerifyUserAccountCompleted
        )
    }

    fun onLockAccount() {
        executeUseCase(useCase = lockAccountUseCase)
    }

    private fun onVerifyUserAccountCompleted(isUnlocked: Boolean) {
        if(!isUnlocked) {
            launchSideEffect(MainSideEffects.AccountIsLockedSideEffect, enableReplay = true)
        }
    }
}

data class MainUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val shouldShowBottomNav: Boolean = false,
    val hasSession: Boolean = true,
    val mainDestinationList: List<BottomNavBarItem> = emptyList()
): UiState<MainUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): MainUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface MainSideEffects: SideEffect {
    data object AccountIsLockedSideEffect: MainSideEffects
}
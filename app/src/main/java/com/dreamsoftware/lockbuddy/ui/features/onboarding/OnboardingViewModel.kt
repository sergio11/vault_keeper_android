package com.dreamsoftware.lockbuddy.ui.features.onboarding

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor() : BrownieViewModel<OnboardingUiState, OnboardingSideEffects>() {
    override fun onGetDefaultState(): OnboardingUiState = OnboardingUiState()

}

data class OnboardingUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val isAuth: Boolean = false
): UiState<OnboardingUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): OnboardingUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface OnboardingSideEffects: SideEffect {
    data object OnSignInSuccessfully: OnboardingSideEffects
}
package com.dreamsoftware.vaultkeeper.ui.features.settings

import androidx.annotation.DrawableRes
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAuthenticateUserDetailUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignOffUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val application: IVaultKeeperApplicationAware,
    private val signOffUseCase: SignOffUseCase,
    private val getAuthenticateUserDetailUseCase: GetAuthenticateUserDetailUseCase,
    private val removeAllCredentialsUseCase: RemoveAllCredentialsUseCase
) : BrownieViewModel<SettingsUiState, SettingsUiSideEffects>(), SettingsScreenActionListener {

    override fun onGetDefaultState(): SettingsUiState = SettingsUiState(
        items = buildItems()
    )

    fun onInit() {
        if(application.isBiometricSupported()) {
            updateState {
                it.copy(items = buildItems(hasBiometric = true))
            }
        }
        executeUseCase(
            useCase = getAuthenticateUserDetailUseCase,
            onSuccess = ::onAuthenticatedUserLoadSuccessfully
        )
    }

    override fun onUpdateSheetVisibility(isVisible: Boolean) {
        updateState { it.copy(showSheet = isVisible) }
    }

    override fun onUpdateCloseSessionDialogVisibility(isVisible: Boolean) {
        updateState { it.copy(showCloseSessionDialog = isVisible) }
    }

    override fun onSettingItemClicked(item: SettingsItem) {
        when(item) {
            SettingsItem.AboutItem -> onUpdateSheetVisibility(isVisible = true)
            is SettingsItem.BiometricItem -> {}
            SettingsItem.LogoutItem -> onUpdateCloseSessionDialogVisibility(isVisible = true)
            SettingsItem.MaterKeyItem -> launchSideEffect(SettingsUiSideEffects.ResetMasterKey)
            SettingsItem.ShareItem -> launchSideEffect(SettingsUiSideEffects.ShareApp)
            SettingsItem.RemoveAllCredentials -> onRemoveAllCredentials()
        }
    }

    override fun onCloseSession() {
        onUpdateCloseSessionDialogVisibility(isVisible = false)
        executeUseCase(useCase = signOffUseCase)
        launchSideEffect(SettingsUiSideEffects.SessionDeleted)
    }

    private fun buildItems(hasBiometric: Boolean = false) = buildList {
        add(SettingsItem.MaterKeyItem)
        if(hasBiometric) {
            add(SettingsItem.BiometricItem(isEnabled = false))
        }
        add(SettingsItem.ShareItem)
        add(SettingsItem.AboutItem)
        add(SettingsItem.LogoutItem)
    }

    private fun onRemoveAllCredentials() {
        executeUseCase(
            useCase = removeAllCredentialsUseCase,
            onSuccess = { onAllCredentialsRemoved() }
        )
    }

    private fun onAllCredentialsRemoved() {

    }

    private fun onAuthenticatedUserLoadSuccessfully(authUserBO: AuthUserBO) {
        updateState { it.copy(authUserBO = authUserBO) }
    }
}

data class SettingsUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showSheet: Boolean = false,
    val showCloseSessionDialog: Boolean = false,
    val items: List<SettingsItem> = emptyList(),
    val authUserBO: AuthUserBO? = null
): UiState<SettingsUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SettingsUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed class SettingsItem(
    val text: String,
    @DrawableRes val icon: Int
) {
    data object MaterKeyItem: SettingsItem(text = "Reset Master Key", icon = R.drawable.icon_lock_open)
    data class BiometricItem(val isEnabled: Boolean): SettingsItem(text = "Fingerprint Unlock", icon = R.drawable.icon_fingerprint)
    data object ShareItem: SettingsItem(text = "Share", icon = R.drawable.icon_share)
    data object AboutItem: SettingsItem(text = "About", icon = R.drawable.icon_info)
    data object RemoveAllCredentials: SettingsItem(text = "Remove all credentials", icon = R.drawable.icon_info)
    data object LogoutItem: SettingsItem(text = "Logout", icon = R.drawable.icon_logout)
}

sealed interface SettingsUiSideEffects: SideEffect {
    data object ShareApp: SettingsUiSideEffects
    data object ResetMasterKey: SettingsUiSideEffects
    data object SessionDeleted: SettingsUiSideEffects
}
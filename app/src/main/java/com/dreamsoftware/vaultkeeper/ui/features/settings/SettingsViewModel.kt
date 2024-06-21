package com.dreamsoftware.vaultkeeper.ui.features.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.domain.model.AuthUserBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAuthenticateUserDetailUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.RemoveAllCredentialsUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SignOffUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.UpdateBiometricAuthStateUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.VerifyBiometricAuthEnabledUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val application: IVaultKeeperApplicationAware,
    private val signOffUseCase: SignOffUseCase,
    private val getAuthenticateUserDetailUseCase: GetAuthenticateUserDetailUseCase,
    private val removeAllCredentialsUseCase: RemoveAllCredentialsUseCase,
    private val verifyBiometricAuthEnabledUseCase: VerifyBiometricAuthEnabledUseCase,
    private val updateBiometricAuthStateUseCase: UpdateBiometricAuthStateUseCase
) : BrownieViewModel<SettingsUiState, SettingsUiSideEffects>(), SettingsScreenActionListener {

    override fun onGetDefaultState(): SettingsUiState = SettingsUiState(
        items = buildItems()
    )

    fun onInit() {
        executeUseCase(
            useCase = verifyBiometricAuthEnabledUseCase,
            onSuccess = ::onVerifyBiometricAuthenticationCompleted
        )
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
            is SettingsItem.BiometricItem -> onUpdateBiometricAuthState(isEnabled = !item.isEnabled)
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

    private fun buildItems(hasBiometric: Boolean = false, isBiometricEnabled: Boolean = false) = buildList {
        add(SettingsItem.MaterKeyItem)
        if(hasBiometric) {
            add(SettingsItem.BiometricItem(isEnabled = isBiometricEnabled))
        }
        add(SettingsItem.ShareItem)
        add(SettingsItem.AboutItem)
        add(SettingsItem.LogoutItem)
    }

    private fun onRemoveAllCredentials() {
        executeUseCase(
            useCase = removeAllCredentialsUseCase
        )
    }

    private fun onAuthenticatedUserLoadSuccessfully(authUserBO: AuthUserBO) {
        updateState { it.copy(authUserBO = authUserBO) }
    }

    private fun onVerifyBiometricAuthenticationCompleted(isEnabled: Boolean) {
        updateState { it.copy(items = buildItems(
            hasBiometric = application.isBiometricSupported(),
            isBiometricEnabled = isEnabled
        )) }
    }

    private fun onUpdateBiometricAuthState(isEnabled: Boolean) {
        executeUseCaseWithParams(
            useCase = updateBiometricAuthStateUseCase,
            params = UpdateBiometricAuthStateUseCase.Params(
                isEnabled = isEnabled
            ),
            onSuccess = {
                onVerifyBiometricAuthenticationCompleted(isEnabled)
            }
        )
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
    @StringRes val textRes: Int,
    @DrawableRes val icon: Int
) {
    data object MaterKeyItem: SettingsItem(textRes = R.string.settings_screen_reset_master_key, icon = R.drawable.icon_lock_open)
    data class BiometricItem(val isEnabled: Boolean): SettingsItem(textRes = R.string.settings_screen_fingerprint_unlock, icon = R.drawable.icon_fingerprint)
    data object ShareItem: SettingsItem(textRes = R.string.settings_screen_share, icon = R.drawable.icon_share)
    data object AboutItem: SettingsItem(textRes = R.string.settings_screen_about, icon = R.drawable.icon_info)
    data object RemoveAllCredentials: SettingsItem(textRes = R.string.settings_screen_remove_all_credentials, icon = R.drawable.icon_info)
    data object LogoutItem: SettingsItem(textRes = R.string.settings_screen_logout, icon = R.drawable.icon_logout)
}

sealed interface SettingsUiSideEffects: SideEffect {
    data object ShareApp: SettingsUiSideEffects
    data object ResetMasterKey: SettingsUiSideEffects
    data object SessionDeleted: SettingsUiSideEffects
}
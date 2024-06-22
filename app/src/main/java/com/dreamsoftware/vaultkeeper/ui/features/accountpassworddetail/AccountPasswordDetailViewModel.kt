package com.dreamsoftware.vaultkeeper.ui.features.accountpassworddetail

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.AccountPasswordDetailErrorMapper
import com.dreamsoftware.vaultkeeper.domain.model.AccountPasswordBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetAccountByIdUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AccountPasswordDetailViewModel @Inject constructor(
    private val getAccountByIdUseCase: GetAccountByIdUseCase,
    @AccountPasswordDetailErrorMapper private val errorMapper: IBrownieErrorMapper,
    private val applicationAware: IVaultKeeperApplicationAware
) : BrownieViewModel<AccountPasswordDetailUiState, AccountPasswordDetailSideEffects>(), AccountPasswordDetailScreenActionListener {

    fun getAccountPasswordById(accountUid: String) {
        executeUseCaseWithParams(
            useCase = getAccountByIdUseCase,
            params = GetAccountByIdUseCase.Params(uid = accountUid),
            onSuccess = ::onFetchAccountPasswordDetailsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): AccountPasswordDetailUiState = AccountPasswordDetailUiState()

    private fun onFetchAccountPasswordDetailsSuccessfully(accountPasswordBO: AccountPasswordBO) {
        updateState {
            it.copy(
                accountUid = accountPasswordBO.uid,
                accountName = accountPasswordBO.accountName,
                username = accountPasswordBO.userName,
                email = accountPasswordBO.email,
                note = accountPasswordBO.note,
                mobileNumber = accountPasswordBO.mobileNumber,
                password = accountPasswordBO.password
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: AccountPasswordDetailUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onCancel() {
        launchSideEffect(AccountPasswordDetailSideEffects.Cancelled)
    }

    override fun onInfoMessageCleared() {
        updateState { it.copy(infoMessage = null) }
    }
}

data class AccountPasswordDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val infoMessage: String? = null,
    val accountUid: String? = null,
    val accountName: String = String.EMPTY,
    val username: String = String.EMPTY,
    val email: String = String.EMPTY,
    val note: String = String.EMPTY,
    val mobileNumber: String = String.EMPTY,
    val password: String = String.EMPTY,
): UiState<AccountPasswordDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): AccountPasswordDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface AccountPasswordDetailSideEffects: SideEffect {
    data object Cancelled: AccountPasswordDetailSideEffects
}
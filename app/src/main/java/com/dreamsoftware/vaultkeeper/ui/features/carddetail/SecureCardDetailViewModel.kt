package com.dreamsoftware.vaultkeeper.ui.features.carddetail

import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.di.SecureCardDetailErrorMapper
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.utils.IVaultKeeperApplicationAware
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SecureCardDetailViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    @SecureCardDetailErrorMapper private val errorMapper: IBrownieErrorMapper,
    private val applicationAware: IVaultKeeperApplicationAware
) : BrownieViewModel<SecureCardDetailUiState, SecureCardDetailSideEffects>(), SecureCardDetailScreenActionListener {

    fun getCardById(cardUid: String) {
        executeUseCaseWithParams(
            useCase = getCardByIdUseCase,
            params = GetCardByIdUseCase.Params(uid = cardUid),
            onSuccess = ::onFetchSecureCardDetailsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): SecureCardDetailUiState = SecureCardDetailUiState()

    private fun onFetchSecureCardDetailsSuccessfully(secureCard: SecureCardBO) {
        updateState {
            it.copy(
                cardUid = secureCard.uid,
                cardNumber = secureCard.cardNumber,
                cardHolderName = secureCard.cardHolderName,
                cardExpiryDate = secureCard.cardExpiryDate,
                cardCVV = secureCard.cardCvv,
                cardProviderEnum = secureCard.cardProvider
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SecureCardDetailUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onCancel() {
        launchSideEffect(SecureCardDetailSideEffects.Cancelled)
    }

    override fun onInfoMessageCleared() {
        updateState { it.copy(infoMessage = null) }
    }
}

data class SecureCardDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val infoMessage: String? = null,
    val message: String? = null,
    val cardUid: String? = null,
    val cardNumber: String = String.EMPTY,
    val cardHolderName: String = String.EMPTY,
    val cardExpiryDate: String = String.EMPTY,
    val cardCVV: String = String.EMPTY,
    val cardProviderEnum: CardProviderEnum = CardProviderEnum.OTHER
): UiState<SecureCardDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SecureCardDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SecureCardDetailSideEffects: SideEffect {
    data object Cancelled: SecureCardDetailSideEffects
}
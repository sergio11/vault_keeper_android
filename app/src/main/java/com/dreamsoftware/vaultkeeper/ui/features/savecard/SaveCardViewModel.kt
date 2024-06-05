package com.dreamsoftware.vaultkeeper.ui.features.savecard

import androidx.annotation.DrawableRes
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveCardUseCase
import com.dreamsoftware.vaultkeeper.utils.cardSuggestions
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveCardViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val saveCardUseCase: SaveCardUseCase
) : BrownieViewModel<SaveCardUiState, SaveCardUiSideEffects>(), SaveCardScreenActionListener {

    fun getCardById(cardUid: String) {
        executeUseCaseWithParams(
            useCase = getCardByIdUseCase,
            params = GetCardByIdUseCase.Params(uid = cardUid),
            onSuccess = ::onFetchSecureCardDetailsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): SaveCardUiState = SaveCardUiState()

    override fun onSaveSecureCard() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = saveCardUseCase,
                params = SaveCardUseCase.Params(
                    cardHolderName = cardHolderName,
                    cardNumber = cardNumber,
                    cardExpiryDate = cardExpiryDate,
                    cardCvv = cardCVV,
                    cardProvider = cardProviderName
                ),
                onSuccess = ::onSecureCardSavedSuccessfully,
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    override fun onCancel() {
        launchSideEffect(SaveCardUiSideEffects.SaveSecureCardCancelled)
    }

    override fun onCardNumberUpdated(newCardNumber: String) {
        updateState { it.copy(cardNumber = newCardNumber) }
    }

    override fun onCardHolderNameUpdated(newCardHolderName: String) {
        updateState { it.copy(cardHolderName = newCardHolderName) }
    }

    override fun onCardExpiryDateUpdated(newCardExpiryDate: String) {
        updateState { it.copy(cardExpiryDate = newCardExpiryDate) }
    }

    override fun onCardCvvUpdated(newCardCvv: String) {
        updateState { it.copy(cardCVV = newCardCvv) }
    }

    override fun onExpandedProviderFieldUpdated(isExpanded: Boolean) {
        updateState { it.copy(
            expandedProviderField = isExpanded, hideKeyboard = true
        ) }
    }

    override fun onCardProviderUpdated(cardProviderName: String, selectedCardImage: Int) {
        updateState { it.copy(
            cardProviderName = cardProviderName,
            selectedCardImage = selectedCardImage,
            expandedProviderField = false,
            hideKeyboard = true
        ) }
    }

    private fun onFetchSecureCardDetailsSuccessfully(secureCard: SecureCardBO) {
        updateState {
            it.copy(
                cardUid = secureCard.cardProvider,
                cardProviderName = secureCard.cardProvider,
                cardNumber = secureCard.cardNumber,
                cardHolderName = secureCard.cardHolderName,
                cardExpiryDate = secureCard.cardExpiryDate,
                cardCVV = secureCard.cardCvv
            )
        }
    }

    private fun onSecureCardSavedSuccessfully(secureCard: SecureCardBO) {
        updateState {
            it.copy(
                isEditScreen = true,
                cardUid = secureCard.cardProvider,
                cardProviderName = secureCard.cardProvider,
                cardNumber = secureCard.cardNumber,
                cardHolderName = secureCard.cardHolderName,
                cardExpiryDate = secureCard.cardExpiryDate,
                cardCVV = secureCard.cardCvv
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SaveCardUiState) =
        uiState.copy(
            error = null
        )
}

data class SaveCardUiState(
    override val isLoading: Boolean = false,
    override val error: String? = null,
    val message: String? = null,
    val isEditScreen: Boolean = false,
    val cardUid: String? = null,
    val cardProviderName: String = "Select Card Provider",
    val cardNumber: String = String.EMPTY,
    val cardHolderName: String = String.EMPTY,
    val cardExpiryDate: String = String.EMPTY,
    val cardCVV: String = String.EMPTY,
    val expanded: Boolean = false,
    val expandedProviderField: Boolean = false,
    val hideKeyboard: Boolean = false,
    @DrawableRes val selectedCardImage: Int = cardSuggestions.first().second,
): UiState<SaveCardUiState>(isLoading, error) {
    override fun copyState(isLoading: Boolean, error: String?): SaveCardUiState =
        copy(isLoading = isLoading, error = error)
}

sealed interface SaveCardUiSideEffects: SideEffect {

    data object SaveSecureCardCancelled: SaveCardUiSideEffects
}
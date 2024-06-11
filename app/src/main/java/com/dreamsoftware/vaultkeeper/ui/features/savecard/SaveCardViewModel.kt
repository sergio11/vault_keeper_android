package com.dreamsoftware.vaultkeeper.ui.features.savecard

import com.dreamsoftware.brownie.component.BrownieDropdownMenuItem
import com.dreamsoftware.brownie.core.BrownieViewModel
import com.dreamsoftware.brownie.core.IBrownieErrorMapper
import com.dreamsoftware.brownie.core.SideEffect
import com.dreamsoftware.brownie.core.UiState
import com.dreamsoftware.brownie.utils.EMPTY
import com.dreamsoftware.vaultkeeper.R
import com.dreamsoftware.vaultkeeper.di.SaveSecureCardErrorMapper
import com.dreamsoftware.vaultkeeper.domain.model.CardProviderEnum
import com.dreamsoftware.vaultkeeper.domain.model.SecureCardBO
import com.dreamsoftware.vaultkeeper.domain.usecase.GetCardByIdUseCase
import com.dreamsoftware.vaultkeeper.domain.usecase.SaveCardUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SaveCardViewModel @Inject constructor(
    private val getCardByIdUseCase: GetCardByIdUseCase,
    private val saveCardUseCase: SaveCardUseCase,
    @SaveSecureCardErrorMapper private val errorMapper: IBrownieErrorMapper
) : BrownieViewModel<SaveCardUiState, SaveCardUiSideEffects>(), SaveCardScreenActionListener {

    fun getCardById(cardUid: String) {
        executeUseCaseWithParams(
            useCase = getCardByIdUseCase,
            params = GetCardByIdUseCase.Params(uid = cardUid),
            onSuccess = ::onFetchSecureCardDetailsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onGetDefaultState(): SaveCardUiState = SaveCardUiState(
        cardProviderMenuItems = listOf(
            BrownieDropdownMenuItem(id = CardProviderEnum.VISA.name, textRes = R.string.visa),
            BrownieDropdownMenuItem(id = CardProviderEnum.MASTERCARD.name, textRes = R.string.mastercard),
            BrownieDropdownMenuItem(id = CardProviderEnum.AMERICAN_EXPRESS.name, textRes = R.string.american_express),
            BrownieDropdownMenuItem(id = CardProviderEnum.RUPAY.name, textRes = R.string.rupay),
            BrownieDropdownMenuItem(id = CardProviderEnum.DINERS_CLUB.name, textRes = R.string.diners_club),
            BrownieDropdownMenuItem(id = CardProviderEnum.OTHER.name, textRes = R.string.other)
        )
    )

    override fun onSaveSecureCard() {
        with(uiState.value) {
            executeUseCaseWithParams(
                useCase = saveCardUseCase,
                params = SaveCardUseCase.Params(
                    uid = cardUid,
                    cardHolderName = cardHolderName,
                    cardNumber = cardNumber,
                    cardExpiryDate = cardExpiryDate,
                    cardCvv = cardCVV,
                    cardProvider = CardProviderEnum.fromName(cardProviderMenuItemSelected?.id)
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

    override fun onCardProviderUpdated(cardProvider: BrownieDropdownMenuItem) {
        updateState { it.copy(cardProviderMenuItemSelected = cardProvider) }
    }

    private fun onFetchSecureCardDetailsSuccessfully(secureCard: SecureCardBO) {
        updateState {
            it.copy(
                isEditScreen = true,
                cardUid = secureCard.uid,
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
                cardUid = secureCard.uid,
                cardNumber = secureCard.cardNumber,
                cardHolderName = secureCard.cardHolderName,
                cardExpiryDate = secureCard.cardExpiryDate,
                cardCVV = secureCard.cardCvv
            )
        }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SaveCardUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class SaveCardUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val message: String? = null,
    val isEditScreen: Boolean = false,
    val cardUid: String? = null,
    val cardNumber: String = String.EMPTY,
    val cardHolderName: String = String.EMPTY,
    val cardExpiryDate: String = String.EMPTY,
    val cardCVV: String = String.EMPTY,
    val expanded: Boolean = false,
    val cardProviderMenuItemSelected: BrownieDropdownMenuItem? = null,
    val cardProviderMenuItems: List<BrownieDropdownMenuItem> = emptyList()
): UiState<SaveCardUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SaveCardUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SaveCardUiSideEffects: SideEffect {

    data object SaveSecureCardCancelled: SaveCardUiSideEffects
}
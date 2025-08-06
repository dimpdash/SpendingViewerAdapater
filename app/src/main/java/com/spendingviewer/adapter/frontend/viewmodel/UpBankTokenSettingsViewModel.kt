package com.spendingviewer.adapter.frontend.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.spendingviewer.infrastructure.secrets.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed class UpBankTokenSettingsUiState {
    object Loading : UpBankTokenSettingsUiState()
    data class Error(val message: String) : UpBankTokenSettingsUiState()
    object Success : UpBankTokenSettingsUiState()
    object InActive : UpBankTokenSettingsUiState()
}

abstract class UpBankTokenSettingsViewModel : ViewModel() {
    protected val _uiState = MutableStateFlow<UpBankTokenSettingsUiState>(UpBankTokenSettingsUiState.InActive) // Initialize with initial state
    val uiState: StateFlow<UpBankTokenSettingsUiState> = _uiState.asStateFlow()
    abstract fun saveApiToken(tokenType: TokenManager.Tokens, apiToken: String)
}

@HiltViewModel
class UpBankTokenSettingsViewModelImpl @Inject constructor(
    private val tokenManager : TokenManager
) : UpBankTokenSettingsViewModel() {
    override fun saveApiToken(tokenType: TokenManager.Tokens, apiToken: String) {
        viewModelScope.launch {
            _uiState.value = UpBankTokenSettingsUiState.Loading
            tokenManager.storeToken(TokenManager.Tokens.UpBank, apiToken)
                .onFailure {
                    _uiState.value = UpBankTokenSettingsUiState.Error(it.message ?: "Unknown error")
                }
                .onSuccess {
                    _uiState.value = UpBankTokenSettingsUiState.Success
                }
        }
    }
}

class UpBankTokenSettingsViewModelStub : UpBankTokenSettingsViewModel() {
    override fun saveApiToken(tokenType: TokenManager.Tokens, apiToken: String) {
        TODO("Not yet implemented")
    }
}
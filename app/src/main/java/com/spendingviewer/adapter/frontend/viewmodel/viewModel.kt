package com.spendingviewer.adapter.frontend.viewmodel

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
inline fun <reified T : ViewModel> myViewModel(): T {
    return if  (equals<T,UpBankTokenSettingsViewModel>()) {
            viewModelSwitch<T,
                    UpBankTokenSettingsViewModelImpl,
                    UpBankTokenSettingsViewModelStub
            >()
        } else {
            throw Exception("Unknown ViewModel")
        }
}

@Composable
inline fun <reified T : ViewModel, reified IMPL : ViewModel, reified STUB : ViewModel> viewModelSwitch(): T {
    return if (LocalInspectionMode.current) viewModel<STUB>() as T
    else hiltViewModel<IMPL>() as T
}

inline fun <reified T, reified T2> equals(): Boolean {
    return T::class.java == T2::class.java
}
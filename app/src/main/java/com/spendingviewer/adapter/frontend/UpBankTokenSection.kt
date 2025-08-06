package com.spendingviewer.adapter.frontend

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.spendingviewer.adapter.frontend.viewmodel.UpBankTokenSettingsUiState
import com.spendingviewer.adapter.frontend.viewmodel.UpBankTokenSettingsViewModel
import com.spendingviewer.adapter.frontend.viewmodel.myViewModel
import com.spendingviewer.infrastructure.secrets.TokenManager

@Composable
fun UpBankTokenSection(upBankViewModel: UpBankTokenSettingsViewModel = myViewModel()) {
    var upBankToken by remember { mutableStateOf("") }
    val uiState by upBankViewModel.uiState.collectAsState()

    Column {
        Text("Up Bank API Token", modifier = Modifier.padding(bottom = 8.dp))
        OutlinedTextField(
            value = upBankToken,
            onValueChange = { upBankToken = it },
            label = { Text("Enter Up Bank API Token") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(24.dp))
        Button(
            onClick = { upBankViewModel.saveApiToken(TokenManager.Tokens.UpBank, upBankToken) },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Up Bank Token")
        }
        when (uiState) {
            is UpBankTokenSettingsUiState.Loading -> Text("Loading")

            is UpBankTokenSettingsUiState.Success -> Text(
                "Up Bank token saved!",
                modifier = Modifier.padding(top = 8.dp)
            )

            is UpBankTokenSettingsUiState.Error -> Text(
                (uiState as UpBankTokenSettingsUiState.Error).message,
                modifier = Modifier.padding(top = 8.dp)
            )

            UpBankTokenSettingsUiState.InActive -> {}
        }
    }}

//@Preview(showBackground = true, name = "Up Bank Token Section Preview")
//@Composable
//fun UpBankTokenSectionPreview() {
//    UpBankTokenSection()
//}
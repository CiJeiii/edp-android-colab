package com.example.labactivity10.presentation.chat

import com.example.labactivity10.domain.model.Message

sealed interface ChatUiState {

    data object Loading : ChatUiState

    data class Ready(
        val messages: List<Message>
    ) : ChatUiState

    data class Error(
        val message: String
    ) : ChatUiState
}

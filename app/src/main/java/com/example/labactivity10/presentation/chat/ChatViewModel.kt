package com.example.labactivity10.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.labactivity10.data.repository.AppResult
import com.example.labactivity10.domain.repository.ChatRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ChatViewModel(
    private val repository: ChatRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<ChatUiState>(ChatUiState.Loading)
    val uiState: StateFlow<ChatUiState> = _uiState.asStateFlow()

    init {
        loadMessages()
    }

    fun loadMessages() {
        viewModelScope.launch {
            _uiState.value = ChatUiState.Loading

            when (val result = repository.getMessages()) {
                is AppResult.Success -> {
                    _uiState.value = ChatUiState.Ready(result.data)
                }

                is AppResult.Error -> {
                    _uiState.value = ChatUiState.Error(result.message)
                }

                is AppResult.Loading -> {
                    _uiState.value = ChatUiState.Loading
                }
            }
        }
    }

    fun sendMessage(sender: String, content: String) {
        if (sender.isBlank() || content.isBlank()) return

        viewModelScope.launch {
            when (val result = repository.sendMessage(sender, content)) {
                is AppResult.Success -> {
                    loadMessages()
                }

                is AppResult.Error -> {
                    _uiState.value = ChatUiState.Error(result.message)
                }

                is AppResult.Loading -> {
                    _uiState.value = ChatUiState.Loading
                }
            }
        }
    }
}

package com.example.labactivity10.presentation.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.labactivity10.data.remote.NetworkModule
import com.example.labactivity10.data.repository.ChatRepositoryImpl
import com.example.labactivity10.domain.repository.ChatRepository

class ChatViewModelFactory : ViewModelProvider.Factory {

    private val repository: ChatRepository =
        ChatRepositoryImpl(NetworkModule.chatApiService)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ChatViewModel::class.java)) {
            return ChatViewModel(repository) as T
        }

        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

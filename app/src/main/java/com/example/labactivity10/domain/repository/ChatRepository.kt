package com.example.labactivity10.domain.repository

import com.example.labactivity10.data.repository.AppResult
import com.example.labactivity10.domain.model.Message

interface ChatRepository {

    suspend fun getMessages(): AppResult<List<Message>>

    suspend fun sendMessage(
        sender: String,
        content: String
    ): AppResult<Message>
}

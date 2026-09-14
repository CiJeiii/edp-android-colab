package com.example.labactivity10.data.repository

import com.example.labactivity10.data.remote.ChatApiService
import com.example.labactivity10.data.remote.dto.NewMessageDto
import com.example.labactivity10.data.remote.dto.toDomain
import com.example.labactivity10.domain.model.Message
import com.example.labactivity10.domain.repository.ChatRepository

class ChatRepositoryImpl(
    private val apiService: ChatApiService
) : ChatRepository {

    override suspend fun getMessages(): AppResult<List<Message>> {
        return try {
            val messages = apiService.getMessages().map { it.toDomain() }
            AppResult.Success(messages)
        } catch (exception: Exception) {
            AppResult.Error(
                message = exception.message ?: "Failed to load messages",
                exception = exception
            )
        }
    }

    override suspend fun sendMessage(
        sender: String,
        content: String
    ): AppResult<Message> {
        return try {
            val newMessage = NewMessageDto(
                sender = sender,
                content = content
            )

            val message = apiService.sendMessage(newMessage).toDomain()
            AppResult.Success(message)
        } catch (exception: Exception) {
            AppResult.Error(
                message = exception.message ?: "Failed to send message",
                exception = exception
            )
        }
    }
}

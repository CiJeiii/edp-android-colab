package com.example.labactivity10.data.remote

import com.example.labactivity10.data.remote.dto.MessageDto
import com.example.labactivity10.data.remote.dto.NewMessageDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ChatApiService {

    @GET("messages")
    suspend fun getMessages(): List<MessageDto>

    @POST("messages")
    suspend fun sendMessage(
        @Body message: NewMessageDto
    ): MessageDto
}

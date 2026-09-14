package com.example.labactivity10.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NewMessageDto(
    val sender: String = "",
    @SerialName("text")
    val content: String = ""
)

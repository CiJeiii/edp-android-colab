package com.example.labactivity10.data.remote.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement

@Serializable
data class MessageDto(
    val id: String = "",
    val sender: String = "",
    @SerialName("text")
    val content: String = "",
    val createdAt: JsonElement? = null
)

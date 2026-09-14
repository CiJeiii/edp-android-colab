package com.example.labactivity10.data.remote.dto

import com.example.labactivity10.domain.model.Message
import kotlinx.serialization.json.jsonPrimitive

fun MessageDto.toDomain(): Message {
    return Message(
        id = id,
        sender = sender,
        content = content,
        createdAt = createdAt?.jsonPrimitive?.content ?: ""
    )
}

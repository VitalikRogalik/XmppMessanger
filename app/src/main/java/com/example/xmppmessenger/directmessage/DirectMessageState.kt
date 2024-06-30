package com.example.xmppmessenger.directmessage

import com.example.xmppmessenger.directmessage.ui.model.MessageItem

data class DirectMessageState(
    val messages: List<MessageItem>,
    val inputText: String
)
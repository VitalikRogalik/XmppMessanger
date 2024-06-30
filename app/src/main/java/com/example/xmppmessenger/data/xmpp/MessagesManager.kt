package com.example.xmppmessenger.data.xmpp

import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import org.jivesoftware.smack.chat2.Chat
import org.jivesoftware.smack.chat2.ChatManager
import org.jivesoftware.smack.chat2.IncomingChatMessageListener
import org.jivesoftware.smack.chat2.OutgoingChatMessageListener
import org.jivesoftware.smack.packet.Message
import org.jivesoftware.smack.packet.MessageBuilder
import org.jivesoftware.smack.tcp.XMPPTCPConnection
import org.jxmpp.jid.EntityBareJid
import org.jxmpp.jid.impl.JidCreate

object MessagesManager {

    private lateinit var chatManager: ChatManager

    private var incomingChatMessageListener: IncomingChatMessageListener? = null
    private var outgoingChatMessageListener: OutgoingChatMessageListener? = null

    private val messagesFlow = MessagesFlow()

    fun init(connection: XMPPTCPConnection) {
        chatManager = ChatManager.getInstanceFor(connection)

        observeIncomingMessages()
        observeOutgoingMessages()
    }

    fun sendMessage(to: String, message: String) {
        val chat = chatManager.chatWith(JidCreate.entityBareFrom(to))
        chat.send(message)
    }

    fun getMessages() = messagesFlow.messages

    private fun observeIncomingMessages() {
        incomingChatMessageListener = IncomingChatMessageListener(messagesFlow::handleIncomingMessage)
        chatManager.addIncomingListener(incomingChatMessageListener)
    }

    private fun observeOutgoingMessages() {
        outgoingChatMessageListener = OutgoingChatMessageListener(messagesFlow::handleOutgoingMessage)
        chatManager.addOutgoingListener(outgoingChatMessageListener)
    }
}


private class MessagesFlow {

    private val _messages: MutableStateFlow<List<MessageModel>> = MutableStateFlow(emptyList())
    val messages = _messages.asStateFlow()

    fun handleIncomingMessage(
        from: EntityBareJid,
        message: Message,
        chat: Chat
    ) {
        _messages.update {
            it + MessageModel(
                id = from.toString(),
                text = message.body
            )
        }
    }

    fun handleOutgoingMessage(
        to: EntityBareJid,
        messageBuilder: MessageBuilder,
        chat: Chat
    ) {
        _messages.update {
            it + MessageModel(
                id = to.toString(),
                text = messageBuilder.body
            )
        }
    }
}

data class MessageModel(
    val id: String,
    val text: String
)

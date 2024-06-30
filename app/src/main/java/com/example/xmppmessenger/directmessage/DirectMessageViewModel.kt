package com.example.xmppmessenger.directmessage

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.xmppmessenger.FromToObject
import com.example.xmppmessenger.data.xmpp.MessageModel
import com.example.xmppmessenger.data.xmpp.MessagesManager
import com.example.xmppmessenger.directmessage.ui.model.MessageItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class DirectMessageViewModel : ViewModel() {

    private val _state = MutableStateFlow(
        DirectMessageState(
            /*messages = listOf(
                MessageItem(isFromMe = true, text = "aaaaaaaaa"),
                MessageItem(isFromMe = true, text = "aaaaaaaaa"),
                MessageItem(isFromMe = true, text = "aaaaaaaaa"),
                MessageItem(isFromMe = false, text = "aaaaaaaaa"),
                MessageItem(isFromMe = true, text = "aaaaaaaaa"),
                MessageItem(isFromMe = false, text = "aaaaaaaaa"),
                MessageItem(isFromMe = false, text = "aaaaaaaaa"),
                MessageItem(isFromMe = false, text = "aaaaaaaaa"),
            ),*/
            messages = emptyList(),
            inputText = ""
        )
    )
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            MessagesManager.getMessages().collectLatest { messages ->
                _state.update {
                    it.copy(
                        messages = messages.map(::createMessageItem)
                    )
                }
            }
        }
    }

    fun onInputMessageChanged(text: String) {
        _state.update {
            it.copy(
                inputText = text
            )
        }
    }

    fun onSendClicked() {
        MessagesManager.sendMessage(
            to = FromToObject.to,
            message = _state.value.inputText
        )
    }

    private fun createMessageItem(message: MessageModel): MessageItem {
        return MessageItem(
            isFromMe = message.id == FromToObject.from,
            text = message.text
        )
    }
}
